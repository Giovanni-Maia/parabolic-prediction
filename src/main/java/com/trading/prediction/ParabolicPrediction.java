package com.trading.prediction;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.trading.base.Prediction;
import com.trading.base.Utils;

public class ParabolicPrediction {

	public static Prediction predict(SortedMap<Long, List<Double>> readings, String coinPair, String interval) {
		
		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli(readings.lastKey());
		instantPrediction = instantPrediction.plus(intervalDuration);
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));
		WeightedObservedPoints points = Utils.fetchPointsSell(readings, 2, 0);
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
		double[] coefs = fitter.fit(points.toList());
		PolynomialFunction function = new PolynomialFunction(coefs);
		System.out.println("FUNCTION: " + function);
		prediction.setSellPriceNowPrediction(function.value(readings.lastKey()));
		prediction.setSellPricePrediction(function.value(instantPrediction.toEpochMilli()));

		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		points = Utils.fetchPointsBuy(readings, 2, 0);
		fitter = PolynomialCurveFitter.create(2);
		coefs = fitter.fit(points.toList());
		function = new PolynomialFunction(coefs);
		System.out.println("FUNCTION: " + function);
		prediction.setBuyPriceNowPrediction(function.value(readings.lastKey()));
		prediction.setBuyPricePrediction(function.value(instantPrediction.toEpochMilli()));
		
		return prediction;
	}

}
