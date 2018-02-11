package com.trading.prediction.parabolic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
	public boolean success;
	public int error;
	public String message;
}
