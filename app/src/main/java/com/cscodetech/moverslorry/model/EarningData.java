package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class EarningData{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("Earning")
	private Earning earning;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public Earning getEarning(){
		return earning;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}