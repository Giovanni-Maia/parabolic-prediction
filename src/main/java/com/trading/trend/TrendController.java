package com.trading.trend;

import java.time.Duration;
import java.time.Instant;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.base.DataReader;
import com.trading.base.Prediction;
import com.trading.base.Utils;

@RestController
@RequestMapping("/trend")
public class TrendController {

	@Autowired
	private DataReader dataReader;

	@RequestMapping(value = "/linear/{coinPair}/{interval}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new PolyTrendLine(1);

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow((long)x[x.length-1]);
		prediction.setPriceNow(y[y.length-1]);
		prediction.setPriceNowPrediction(trendLine.predict(x[x.length-1]));

		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

}
