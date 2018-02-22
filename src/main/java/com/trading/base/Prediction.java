package com.trading.base;

public class Prediction {
	private String coinPair;
	private long timestampNow;
	private double buyPriceNow, sellPriceNow, buyPriceNowPrediction;
	private long timestampPrediction;
	private double buyPricePrediction, profitPrediction;
	private double trendPercentage;

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

	public double getBuyPriceNowPrediction() {
		return buyPriceNowPrediction;
	}

	public void setBuyPriceNowPrediction(double buyPriceNowPrediction) {
		if (buyPricePrediction != 0) {
			setProfitPrediction(buyPricePrediction - buyPriceNowPrediction);
			setTrendPercentage((getProfitPrediction() / buyPriceNowPrediction) * 100d);
		}
		this.buyPriceNowPrediction = buyPriceNowPrediction;
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
		if (buyPriceNowPrediction != 0) {
			setProfitPrediction(buyPricePrediction - buyPriceNowPrediction);
			setTrendPercentage((getProfitPrediction() / buyPriceNowPrediction) * 100d);
		}
		this.buyPricePrediction = buyPricePrediction;
	}

	public double getSellPriceNow() {
		return sellPriceNow;
	}

	public void setSellPriceNow(double sellPriceNow) {
		this.sellPriceNow = sellPriceNow;
	}

	public double getProfitPrediction() {
		return profitPrediction;
	}

	public void setProfitPrediction(double profitPrediction) {
		this.profitPrediction = profitPrediction;
	}

	public double getTrendPercentage() {
		return trendPercentage;
	}

	public void setTrendPercentage(double trendPercentage) {
		this.trendPercentage = trendPercentage;
	}
}
