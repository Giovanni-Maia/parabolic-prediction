package com.trading.prediction.handler;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.trading.prediction.base.Prediction;

public class CubicPrediction {

	public static Prediction predict(SortedMap<Long, Double> readings, String coinPair, String interval) {
		WeightedObservedPoints points = fetchParabolicPoints(readings);

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

	private enum Trend {
		UP, DOWN
	}

	public static WeightedObservedPoints fetchParabolicPoints(SortedMap<Long, Double> readings) {
		WeightedObservedPoints points = new WeightedObservedPoints();

		boolean alreadyFoundFirstChange = false;

		Trend currentTrend = null;

		List<Long> keys = new ArrayList<>();
		keys.addAll(readings.keySet());

		Long lastKey = keys.get(keys.size() - 1);

		for (Long key : readings.keySet()) {
			System.out.println("CANDIDATE POINT: " + key + ", " + readings.get(key));
		}

		for (int i = keys.size() - 2; i >= 0; i--) {
			Long key = keys.get(i);

			if (currentTrend == null) {
				if (readings.get(key) >= readings.get(lastKey)) {
					currentTrend = Trend.UP;
				} else {
					currentTrend = Trend.DOWN;
				}
				lastKey = key;
				continue;
			}
			if (readings.get(key) >= readings.get(lastKey)) {
				if (currentTrend == Trend.DOWN) {
					if (!alreadyFoundFirstChange) {
						currentTrend = Trend.UP;
						alreadyFoundFirstChange = true;
					} else {
						break;
					}
				}
			} else {
				if (currentTrend == Trend.UP) {
					if (!alreadyFoundFirstChange) {
						currentTrend = Trend.DOWN;
						alreadyFoundFirstChange = true;
					} else {
						break;
					}
				}
			}
			lastKey = key;
		}

		for (Entry<Long, Double> entry : readings.tailMap(lastKey).entrySet()) {
			System.out.println("FINAL POINT: " + entry.getKey() + ", " + entry.getValue());
			points.add(entry.getKey(), entry.getValue());
		}

		return points;
	}

}
