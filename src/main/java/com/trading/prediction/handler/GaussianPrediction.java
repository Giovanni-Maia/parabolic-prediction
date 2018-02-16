package com.trading.prediction.handler;

import java.time.Duration;
import java.time.Instant;
import java.util.SortedMap;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.trading.prediction.base.Prediction;

public class GaussianPrediction {

	public static Prediction predict(SortedMap<Long, Double> readings, String coinPair, String interval) {
		WeightedObservedPoints points = PredictionUtils.fetchPoints(readings, 3, 1);

		GaussianCurveFitter fitter = GaussianCurveFitter.create();
		double[] coefs = fitter.fit(points.toList());

		Gaussian function = new Gaussian(coefs[0], coefs[1], coefs[2]);
		System.out.println("FUNCTION: " + function);
		UnivariateFunction firstDerivative = function.derivative();

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.now();
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow((long) points.toList().get(points.toList().size() - 1).getX());
		prediction.setPriceNow(points.toList().get(points.toList().size() - 1).getY());
		prediction.setErrorNow(function.value(points.toList().get(points.toList().size() - 1).getX())
				- points.toList().get(points.toList().size() - 1).getY());
		prediction.setFirstDerivativeNow(firstDerivative.value(points.toList().get(points.toList().size() - 1).getX()));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setPricePrediction(function.value(instantPrediction.toEpochMilli()));
		prediction.setFirstDerivativePrediction(firstDerivative.value(instantPrediction.toEpochMilli()));

		return prediction;
	}

}
