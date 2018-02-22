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

		double[] x = Utils.getXs(readings);
		double[] y = Utils.getYs(readings);

		WeightedObservedPoints points = new WeightedObservedPoints();
		for (int i = 0; i < x.length; i++) {
			log.info("FINAL POINT: " + x[i] + ", " + y[i]);
			points.add(x[i], y[i]);
		}

		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
		double[] coefs = fitter.fit(points.toList());

		PolynomialFunction function = new PolynomialFunction(coefs);
		log.info("FUNCTION: " + function);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long) points.toList().get(points.toList().size() - 1).getX());
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(function.value(readings.lastKey()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(function.value(instantPrediction.toEpochMilli()));
		return prediction;
	}
}
