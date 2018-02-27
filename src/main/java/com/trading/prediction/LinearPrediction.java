package com.trading.prediction;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trading.base.Prediction;
import com.trading.base.Utils;

public class LinearPrediction {

	private static final Logger log = LoggerFactory.getLogger(LinearPrediction.class);

	public static Prediction predict(SortedMap<Long, List<Double>> readings, String coinPair, String interval) {
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());

		double[] x = Utils.getXs(readings);

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		double[] y = Utils.getYsBuy(readings);
		WeightedObservedPoints points = new WeightedObservedPoints();
		for (int i = 0; i < x.length; i++) {
			log.info("FINAL POINT: " + x[i] + ", " + y[i]);
			points.add(x[i], y[i]);
		}
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
		double[] coefs = fitter.fit(points.toList());
		PolynomialFunction function = new PolynomialFunction(coefs);
		log.info("FUNCTION: " + function);
		prediction.setBuyPricePrediction(function.value(instantPrediction.toEpochMilli()));
		prediction.setBuyPriceNowPrediction(function.value(readings.lastKey()));

		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		y = Utils.getYsSell(readings);
		points = new WeightedObservedPoints();
		for (int i = 0; i < x.length; i++) {
			log.info("FINAL POINT: " + x[i] + ", " + y[i]);
			points.add(x[i], y[i]);
		}
		fitter = PolynomialCurveFitter.create(1);
		coefs = fitter.fit(points.toList());
		function = new PolynomialFunction(coefs);
		log.info("FUNCTION: " + function);
		prediction.setSellPricePrediction(function.value(instantPrediction.toEpochMilli()));
		prediction.setSellPriceNowPrediction(function.value(readings.lastKey()));

		return prediction;
	}
}
