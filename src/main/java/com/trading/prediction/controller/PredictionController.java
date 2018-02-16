package com.trading.prediction.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.trading.prediction.base.HistoryData;
import com.trading.prediction.base.HistoryData.Payload.Aggregations.All.Buckets;
import com.trading.prediction.base.Prediction;
import com.trading.prediction.handler.CubicPrediction;
import com.trading.prediction.handler.GaussianPrediction;
import com.trading.prediction.handler.ParabolicPrediction;

@RestController
@RequestMapping("/prediction")
public class PredictionController {

	private RestTemplate restTemplate;

	public PredictionController() throws Exception {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

		HttpClient httpClient = HttpClientBuilder.create().build();
		factory.setHttpClient(httpClient);
		restTemplate = new RestTemplate(factory);
		openPort();
	}

	private String getMyIPv4() throws Exception {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		return in.readLine();
	}

	private void openPort() throws Exception {

		restTemplate.getForEntity(
				"http://bot.cryptoinvest.money:31337/trading/auth/login/apikey/sga!/environment/sandbox", String.class);

		restTemplate.getForEntity("http://bot.cryptoinvest.money:31337/management/ec2/allow/giovanni/ip/" + getMyIPv4(),
				String.class);
	}

	private SortedMap<Long, Double> fetchReadingsOrdered(String coinPair, String interval) {
		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());

		Instant instantFrom = Instant.now();
		instantFrom = instantFrom.minus(intervalDuration.multipliedBy(20));

		ResponseEntity<HistoryData> response = restTemplate
				.getForEntity("http://bot.cryptoinvest.money:31337/trading/history/prices/exchange/kucoin/pair/"
						+ coinPair + "/aggregation/" + interval + "/from/" + instantFrom.toEpochMilli() + "/to/"
						+ Instant.now().toEpochMilli(), HistoryData.class);

		SortedMap<Long, Double> readings = new TreeMap<>();
		for (Buckets bucket : response.getBody().payload.aggregations.all.buckets) {
			readings.put(bucket.key, bucket.buy.value);
		}
		return readings;
	}

	@RequestMapping(value = "/parabolic/{coinPair}/{interval}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = fetchReadingsOrdered(coinPair, interval);

		Prediction prediction = ParabolicPrediction.predict(readings, coinPair, interval);
		return prediction;
	}

	@RequestMapping(value = "/cubic/{coinPair}/{interval}")
	public Prediction spline(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = fetchReadingsOrdered(coinPair, interval);

		Prediction prediction = CubicPrediction.predict(readings, coinPair, interval);
		return prediction;
	}

	@RequestMapping(value = "/gaussian/{coinPair}/{interval}")
	public Prediction gaussian(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = fetchReadingsOrdered(coinPair, interval);

		Prediction prediction = GaussianPrediction.predict(readings, coinPair, interval);
		return prediction;
	}
}
