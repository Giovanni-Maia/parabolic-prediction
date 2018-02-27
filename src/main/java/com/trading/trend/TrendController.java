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

	@RequestMapping(value = "/linear/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction linear(@PathVariable("coinPair") String coinPair,
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsDataIntervalOrdered(coinPair, intervalData);
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + intervalPrediction.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		TrendLine trendLine = new PolyTrendLine(1);
		trendLine.setValues(y, x);
		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		trendLine = new PolyTrendLine(1);
		trendLine.setValues(y, x);
		prediction.setSellPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setSellPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		return prediction;
	}

	@RequestMapping(value = "/parabolic/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair, 
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsDataIntervalOrdered(coinPair, intervalData);
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + intervalPrediction.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		TrendLine trendLine = new PolyTrendLine(2);
		trendLine.setValues(y, x);
		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		trendLine = new PolyTrendLine(2);
		trendLine.setValues(y, x);
		prediction.setSellPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setSellPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		return prediction;
	}

	@RequestMapping(value = "/cubic/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction cubic(@PathVariable("coinPair") String coinPair, 
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsDataIntervalOrdered(coinPair, intervalData);
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + intervalPrediction.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		TrendLine trendLine = new PolyTrendLine(3);
		trendLine.setValues(y, x);
		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		trendLine = new PolyTrendLine(3);
		trendLine.setValues(y, x);
		prediction.setSellPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setSellPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		return prediction;
	}

	@RequestMapping(value = "/exp/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction exp(@PathVariable("coinPair") String coinPair, 
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsDataIntervalOrdered(coinPair, intervalData);
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + intervalPrediction.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		TrendLine trendLine = new ExpTrendLine();
		trendLine.setValues(y, x);
		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		trendLine = new ExpTrendLine();
		trendLine.setValues(y, x);
		prediction.setSellPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setSellPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		return prediction;
	}

	@RequestMapping(value = "/power/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction power(@PathVariable("coinPair") String coinPair, 
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsDataIntervalOrdered(coinPair, intervalData);
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + intervalPrediction.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		TrendLine trendLine = new PowerTrendLine();
		trendLine.setValues(y, x);
		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		trendLine = new PowerTrendLine();
		trendLine.setValues(y, x);
		prediction.setSellPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setSellPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		return prediction;
	}

	@RequestMapping(value = "/log/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction log(@PathVariable("coinPair") String coinPair, 
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsDataIntervalOrdered(coinPair, intervalData);
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + intervalPrediction.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		TrendLine trendLine = new LogTrendLine();
		trendLine.setValues(y, x);
		prediction.setBuyPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setBuyPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		trendLine = new LogTrendLine();
		trendLine.setValues(y, x);
		prediction.setSellPriceNowPrediction(trendLine.predict(readings.lastKey()));
		prediction.setSellPricePrediction(trendLine.predict(instantPrediction.toEpochMilli()));

		return prediction;
	}

}
