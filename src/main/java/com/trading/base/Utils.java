package com.trading.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class Utils {

	private enum Trend {
		UP, DOWN
	}

	public static WeightedObservedPoints fetchPoints(SortedMap<Long, Double> readings, int numberOfKnots, int percentageVariation) {
		double PERCENTAGE_VARIATION = ((double)percentageVariation)/100d;
		
		WeightedObservedPoints points = new WeightedObservedPoints();

		int numberOfKnotsFound = 1;

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
			if (readings.get(key) >= (readings.get(lastKey)*(1.0d+PERCENTAGE_VARIATION))) {
				if (currentTrend == Trend.DOWN) {
					numberOfKnotsFound += 1;
					if (numberOfKnots != numberOfKnotsFound) {
						currentTrend = Trend.UP;
					} else {
						break;
					}
				}
			} else if(readings.get(key) <= (readings.get(lastKey)*(1.0d-PERCENTAGE_VARIATION))){
				if (currentTrend == Trend.UP) {
					numberOfKnotsFound += 1;
					if (numberOfKnots != numberOfKnotsFound) {
						currentTrend = Trend.DOWN;
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

	public static double[] getXs(SortedMap<Long, Double> readings) {
		double[] x = new double[readings.size()];
		int i = 0;
		for(Long key : readings.keySet()) {
			x[i] = key;
			i++;
		}
		return x;
	}

	public static double[] getYs(SortedMap<Long, Double> readings) {
		double[] y = new double[readings.size()];
		int i = 0;
		for(Double value : readings.values()) {
			y[i] = value;
			i++;
		}
		return y;
	}
}
