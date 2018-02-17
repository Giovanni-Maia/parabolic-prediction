package com.trading.base;

import java.text.DecimalFormat;

public class Prediction {
	private String coinPair;
	private long timestampNow;
	private double priceNow, priceNowPrediction;
	private long timestampPrediction;
	private double pricePrediction;
	private String trend;
	
	public String getCoinPair() {
		return coinPair;
	}
	public void setCoinPair(String coinPair) {
		this.coinPair = coinPair;
	}
	public long getTimestampNow() {
		return timestampNow;
	}
	public void setTimestampNow(long timestampNow) {
		this.timestampNow = timestampNow;
	}
	public double getPriceNow() {
		return priceNow;
	}
	public void setPriceNow(double priceNow) {
		this.priceNow = priceNow;
	}
	public double getPriceNowPrediction() {
		return priceNowPrediction;
	}
	public void setPriceNowPrediction(double priceNowPrediction) {
		if(pricePrediction != 0) {
			setTrend(((pricePrediction - priceNowPrediction)/priceNowPrediction)*100d);
		}
		this.priceNowPrediction = priceNowPrediction;
	}
	public long getTimestampPrediction() {
		return timestampPrediction;
	}
	public void setTimestampPrediction(long timestampPrediction) {
		this.timestampPrediction = timestampPrediction;
	}
	public double getPricePrediction() {
		return pricePrediction;
	}
	public void setPricePrediction(double pricePrediction) {
		if(priceNowPrediction != 0) {
			setTrend(((pricePrediction - priceNowPrediction)/priceNowPrediction)*100d);
		}
		this.pricePrediction = pricePrediction;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(double trend) {
		DecimalFormat df2 = new DecimalFormat("#0.00");
		this.trend = df2.format(trend)+"%";
	}
}
