package com.trading.base;

public class Prediction {
	private String coinPair;
	private long timestampNow;
	private double buyPriceNow, sellPriceNow, buyPriceNowPrediction, sellPriceNowPrediction;
	private long timestampPrediction;
	private double buyPricePrediction, sellPricePrediction, profitPrediction, profitPredictionPercentage;

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

	public double getBuyPriceNow() {
		return buyPriceNow;
	}

	public void setBuyPriceNow(double buyPriceNow) {
		this.buyPriceNow = buyPriceNow;
	}

	public double getSellPriceNow() {
		return sellPriceNow;
	}

	public void setSellPriceNow(double sellPriceNow) {
		this.sellPriceNow = sellPriceNow;
	}

	public double getBuyPriceNowPrediction() {
		return buyPriceNowPrediction;
	}

	public void setBuyPriceNowPrediction(double buyPriceNowPrediction) {
		this.buyPriceNowPrediction = buyPriceNowPrediction;
	}

	public double getSellPriceNowPrediction() {
		return sellPriceNowPrediction;
	}

	public void setSellPriceNowPrediction(double sellPriceNowPrediction) {
		this.sellPriceNowPrediction = sellPriceNowPrediction;
	}

	public long getTimestampPrediction() {
		return timestampPrediction;
	}

	public void setTimestampPrediction(long timestampPrediction) {
		this.timestampPrediction = timestampPrediction;
	}

	public double getBuyPricePrediction() {
		return buyPricePrediction;
	}

	public void setBuyPricePrediction(double buyPricePrediction) {
		this.buyPricePrediction = buyPricePrediction;
	}

	public double getSellPricePrediction() {
		return sellPricePrediction;
	}

	public void setSellPricePrediction(double sellPricePrediction) {
		this.sellPricePrediction = sellPricePrediction;
	}

	public double getProfitPrediction() {
		return profitPrediction;
	}

	public void setProfitPrediction(double profitPrediction) {
		this.profitPrediction = profitPrediction;
	}

	public double getProfitPredictionPercentage() {
		return profitPredictionPercentage;
	}

	public void setProfitPredictionPercentage(double profitPredictionPercentage) {
		this.profitPredictionPercentage = profitPredictionPercentage;
	}
}
