package com.trading.trend;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
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
	public Prediction linear(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new PolyTrendLine(1);

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

	@RequestMapping(value = "/parabolic/{coinPair}/{interval}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new PolyTrendLine(2);

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

	@RequestMapping(value = "/cubic/{coinPair}/{interval}")
	public Prediction cubic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new PolyTrendLine(3);

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

	@RequestMapping(value = "/exp/{coinPair}/{interval}")
	public Prediction exp(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new ExpTrendLine();

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

	@RequestMapping(value = "/power/{coinPair}/{interval}")
	public Prediction power(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new PowerTrendLine();

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

	@RequestMapping(value = "/log/{coinPair}/{interval}")
	public Prediction log(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsOrdered(coinPair, interval);
		
		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		TrendLine trendLine = new LogTrendLine();

		trendLine.setValues(y, x);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)x[x.length-1]);
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));
		return prediction;
	}

}
