package com.trading.prediction;

import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.base.DataReader;
import com.trading.base.Prediction;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
	
	@Autowired
	private DataReader dataReader;

	@RequestMapping(value = "/parabolic/{coinPair}/{interval}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = dataReader.fetchReadingsOrdered(coinPair, interval);

		Prediction prediction = ParabolicPrediction.predict(readings, coinPair, interval);
		return prediction;
	}

	@RequestMapping(value = "/cubic/{coinPair}/{interval}")
	public Prediction spline(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = dataReader.fetchReadingsOrdered(coinPair, interval);

		Prediction prediction = CubicPrediction.predict(readings, coinPair, interval);
		return prediction;
	}

	@RequestMapping(value = "/gaussian/{coinPair}/{interval}")
	public Prediction gaussian(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, Double> readings = dataReader.fetchReadingsOrdered(coinPair, interval);

		Prediction prediction = GaussianPrediction.predict(readings, coinPair, interval);
		return prediction;
	}
}
