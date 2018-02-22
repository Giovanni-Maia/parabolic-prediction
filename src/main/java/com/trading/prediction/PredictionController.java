package com.trading.prediction;

import java.util.List;
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

	@RequestMapping(value = "/linear/{coinPair}/{intervalData}/{intervalPrediction}")
	public Prediction linear(@PathVariable("coinPair") String coinPair,
			@PathVariable("intervalData") String intervalData,
			@PathVariable("intervalPrediction") String intervalPrediction) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsAgg5mDataIntervalOrdered(coinPair,
				intervalData);

		Prediction prediction = LinearPrediction.predict(readings, coinPair, intervalPrediction);
		return prediction;
	}

	@RequestMapping(value = "/parabolic/{coinPair}/{interval}")
	public Prediction parabolic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsAggIntervalData20xIntervalOrdered(coinPair,
				interval);

		Prediction prediction = ParabolicPrediction.predict(readings, coinPair, interval);
		return prediction;
	}

	@RequestMapping(value = "/cubic/{coinPair}/{interval}")
	public Prediction cubic(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsAggIntervalData20xIntervalOrdered(coinPair,
				interval);

		Prediction prediction = CubicPrediction.predict(readings, coinPair, interval);
		return prediction;
	}

	@RequestMapping(value = "/gaussian/{coinPair}/{interval}")
	public Prediction gaussian(@PathVariable("coinPair") String coinPair, @PathVariable("interval") String interval) {

		SortedMap<Long, List<Double>> readings = dataReader.fetchReadingsAggIntervalData20xIntervalOrdered(coinPair,
				interval);

		Prediction prediction = GaussianPrediction.predict(readings, coinPair, interval);
		return prediction;
	}
}
