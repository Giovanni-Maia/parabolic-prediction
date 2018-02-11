package com.trading.prediction.parabolic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.trading.prediction.parabolic.HistoryData.Payload.Aggregations.All.Buckets;

@RestController
@RequestMapping("/prediction/parabolic")
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

	@RequestMapping(value = "/{coinPair}/{interval}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair,
			@PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = fetchReadingsOrdered(coinPair, interval);

		WeightedObservedPoints points = fetchParabolicPoints(readings);

		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
		double[] coefs = fitter.fit(points.toList());

		PolynomialFunction function = new PolynomialFunction(coefs);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.now();
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);
		prediction.setTimestamp(instantPrediction.toEpochMilli());
		prediction.setPrice(function.value(instantPrediction.toEpochMilli()));
		return prediction;
	}

	private enum Trend {
		UP, DOWN
	}

	private WeightedObservedPoints fetchParabolicPoints(SortedMap<Long, Double> readings) {
		WeightedObservedPoints points = new WeightedObservedPoints();

		Long firstZeroDerivative = null;
		Long secondZeroDerivative = null;

		Long lastKey = null;
		Trend currentTrend = null;

		for (Long key : readings.keySet()) {
			System.out.println("CANDIDATE POINT: "+key+", "+readings.get(key));
			if (lastKey == null) {
				lastKey = key;
				firstZeroDerivative = key;
				continue;
			}
			if (currentTrend == null) {
				if (readings.get(key) >= readings.get(lastKey)) {
					currentTrend = Trend.UP;
				}
				else {
					currentTrend = Trend.DOWN;					
				}
				lastKey = key;
				continue;
			}
			if (readings.get(key) >= readings.get(lastKey)) {
				if(currentTrend == Trend.DOWN) {
					secondZeroDerivative = firstZeroDerivative;
					firstZeroDerivative = lastKey;
				}
			}
			else {
				if(currentTrend == Trend.UP) {
					secondZeroDerivative = firstZeroDerivative;
					firstZeroDerivative = lastKey;
				}			
			}
			lastKey = key;
		}
		
		for(Entry<Long, Double> entry : readings.tailMap(secondZeroDerivative).entrySet()) {
			System.out.println("FINAL POINT: "+entry.getKey()+", "+entry.getValue());
			points.add(entry.getKey(), entry.getValue());
		}

		return points;
	}

	private SortedMap<Long, Double> fetchReadingsOrdered(String coinPair, String interval) {
		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());

		Instant instantFrom = Instant.now();
		instantFrom = instantFrom.minus(intervalDuration.multipliedBy(10));

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
}
