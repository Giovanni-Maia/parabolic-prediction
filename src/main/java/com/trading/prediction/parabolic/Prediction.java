package com.trading.prediction.parabolic;

public class Prediction {
	private long timestamp;
	private String coinPair;
	private double price;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getCoinPair() {
		return coinPair;
	}
	public void setCoinPair(String coinPair) {
		this.coinPair = coinPair;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
