package com.trading.prediction;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.trading.base.Prediction;
import com.trading.base.Utils;

public class GaussianPrediction {

	public static Prediction predict(SortedMap<Long, List<Double>> readings, String coinPair, String interval) {
		WeightedObservedPoints points = Utils.fetchPoints(readings, 3, 1);

		GaussianCurveFitter fitter = GaussianCurveFitter.create();
		double[] coefs = fitter.fit(points.toList());

		Gaussian function = new Gaussian(coefs[0], coefs[1], coefs[2]);
		System.out.println("FUNCTION: " + function);

		Duration intervalDuration = Duration.parse("PT" + interval.toUpperCase());
		Instant instantPrediction = Instant.ofEpochMilli((long)points.toList().get(points.toList().size() - 1).getX());
		instantPrediction = instantPrediction.plus(intervalDuration);

		Prediction prediction = new Prediction();
		prediction.setCoinPair(coinPair);

		prediction.setTimestampNow(readings.lastKey());
		prediction.setBuyPriceNow(readings.get(readings.lastKey()).get(0));
		prediction.setSellPriceNow(readings.get(readings.lastKey()).get(1));

		prediction.setBuyPriceNowPrediction(function.value(readings.get(readings.lastKey()).get(0)));
		prediction.setTimestampPrediction(instantPrediction.toEpochMilli());
		prediction.setBuyPricePrediction(function.value(instantPrediction.toEpochMilli()));
		return prediction;
	}

}
