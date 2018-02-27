package com.trading.base;

public class Prediction {
	private String coinPair;
	private long timestampNow;
	private double buyPriceNow, sellPriceNow, buyPriceNowPrediction, sellPriceNowPrediction;
	private long timestampPrediction;
	private double buyPricePrediction, sellPricePrediction, buyProfitPrediction, buyProfitPredictionPercentage,
			sellProfitPrediction, sellProfitPredictionPercentage;

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
		if (buyPricePrediction != 0) {
			setBuyProfitPrediction(buyPricePrediction - buyPriceNowPrediction);
			setBuyProfitPredictionPercentage((getBuyProfitPrediction() / buyPriceNowPrediction) * 100d);
		}
		this.buyPriceNowPrediction = buyPriceNowPrediction;
	}

	public double getSellPriceNowPrediction() {
		return sellPriceNowPrediction;
	}

	public void setSellPriceNowPrediction(double sellPriceNowPrediction) {
		if (sellPricePrediction != 0) {
			setSellProfitPrediction(sellPricePrediction - sellPriceNowPrediction);
			setSellProfitPredictionPercentage((getSellProfitPrediction() / sellPriceNowPrediction) * 100d);
		}
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
		if (buyPriceNowPrediction != 0) {
			setBuyProfitPrediction(buyPricePrediction - buyPriceNowPrediction);
			setBuyProfitPredictionPercentage((getBuyProfitPrediction() / buyPriceNowPrediction) * 100d);
		}
		this.buyPricePrediction = buyPricePrediction;
	}

	public double getSellPricePrediction() {
		return sellPricePrediction;
	}

	public void setSellPricePrediction(double sellPricePrediction) {
		if (sellPriceNowPrediction != 0) {
			setSellProfitPrediction(sellPricePrediction - sellPriceNowPrediction);
			setSellProfitPredictionPercentage((getSellProfitPrediction() / sellPriceNowPrediction) * 100d);
		}
		this.sellPricePrediction = sellPricePrediction;
	}

	public double getBuyProfitPrediction() {
		return buyProfitPrediction;
	}

	public void setBuyProfitPrediction(double buyProfitPrediction) {
		this.buyProfitPrediction = buyProfitPrediction;
	}

	public double getBuyProfitPredictionPercentage() {
		return buyProfitPredictionPercentage;
	}

	public void setBuyProfitPredictionPercentage(double buyProfitPredictionPercentage) {
		this.buyProfitPredictionPercentage = buyProfitPredictionPercentage;
	}

	public double getSellProfitPrediction() {
		return sellProfitPrediction;
	}

	public void setSellProfitPrediction(double sellProfitPrediction) {
		this.sellProfitPrediction = sellProfitPrediction;
	}

	public double getSellProfitPredictionPercentage() {
		return sellProfitPredictionPercentage;
	}

	public void setSellProfitPredictionPercentage(double sellProfitPredictionPercentage) {
		this.sellProfitPredictionPercentage = sellProfitPredictionPercentage;
	}
}
