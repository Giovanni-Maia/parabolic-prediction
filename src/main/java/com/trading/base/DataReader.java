package com.trading.base;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.trading.base.HistoryData.Payload.Aggregations.All.Buckets;

@Component
public class DataReader {

	@Autowired
	private RestTemplate restTemplate;
	
	public SortedMap<Long, List<Double>> fetchReadingsOrdered(String coinPair, String interval) {
		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());

		Instant instantFrom = Instant.now();
		instantFrom = instantFrom.minus(intervalDuration.multipliedBy(20));

		ResponseEntity<HistoryData> response = restTemplate
				.getForEntity("http://bot.cryptoinvest.money:31337/trading/history/prices/exchange/kucoin/pair/"
						+ coinPair + "/aggregation/" + interval + "/from/" + instantFrom.toEpochMilli() + "/to/"
						+ Instant.now().toEpochMilli(), HistoryData.class);

		SortedMap<Long, List<Double>> readings = new TreeMap<>();
		for (Buckets bucket : response.getBody().payload.aggregations.all.buckets) {
			List<Double> buySell = new ArrayList<>();
			buySell.add(bucket.buy.value);
			buySell.add(bucket.sell.value);
			readings.put(bucket.key, buySell);
		}
		return readings;
	}
}
