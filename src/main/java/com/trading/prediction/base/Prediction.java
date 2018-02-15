package com.trading.prediction.base;

public class Prediction {
	private String coinPair;
	private long timestampNow;
	private double priceNow, firstDerivativeNow, secondDerivativeNow;
	private double errorNow;
	private long timestampPrediction;
	private double pricePrediction, firstDerivativePrediction, secondDerivativePrediction;
	
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
	public double getFirstDerivativeNow() {
		return firstDerivativeNow;
	}
	public void setFirstDerivativeNow(double firstDerivativeNow) {
		this.firstDerivativeNow = firstDerivativeNow;
	}
	public double getSecondDerivativeNow() {
		return secondDerivativeNow;
	}
	public void setSecondDerivativeNow(double secondDerivativeNow) {
		this.secondDerivativeNow = secondDerivativeNow;
	}
	public double getErrorNow() {
		return errorNow;
	}
	public void setErrorNow(double errorNow) {
		this.errorNow = errorNow;
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
		this.pricePrediction = pricePrediction;
	}
	public double getFirstDerivativePrediction() {
		return firstDerivativePrediction;
	}
	public void setFirstDerivativePrediction(double firstDerivativePrediction) {
		this.firstDerivativePrediction = firstDerivativePrediction;
	}
	public double getSecondDerivativePrediction() {
		return secondDerivativePrediction;
	}
	public void setSecondDerivativePrediction(double secondDerivativePrediction) {
		this.secondDerivativePrediction = secondDerivativePrediction;
	}
	
	
}
