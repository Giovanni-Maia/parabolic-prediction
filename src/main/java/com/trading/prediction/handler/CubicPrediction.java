package com.trading.prediction.handler;

import java.time.Duration;
import java.time.Instant;
import java.util.SortedMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.trading.prediction.base.Prediction;

public class CubicPrediction {

	public static Prediction predict(SortedMap<Long, Double> readings, String coinPair, String interval) {
		WeightedObservedPoints points = PredictionUtils.fetchPoints(readings, 3, 1);

		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);
		double[] coefs = fitter.fit(points.toList());

		PolynomialFunction function = new PolynomialFunction(coefs);
		System.out.println("FUNCTION: " + function);
		PolynomialFunction firstDerivative = (PolynomialFunction) function.derivative();
		PolynomialFunction secondDerivative = (PolynomialFunction) firstDerivative.derivative();

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
		prediction
				.setSecondDerivativeNow(secondDerivative.value(points.toList().get(points.toList().size() - 1).getX()));

		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setPricePrediction(function.value(instantPrediction.toEpochMilli()));
		prediction.setFirstDerivativePrediction(firstDerivative.value(instantPrediction.toEpochMilli()));
		prediction.setSecondDerivativePrediction(secondDerivative.value(instantPrediction.toEpochMilli()));
		return prediction;
	}
}
