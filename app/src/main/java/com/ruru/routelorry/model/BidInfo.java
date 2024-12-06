package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

public class BidInfo{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("lorrizprofile")
	private Lorrizprofile lorrizprofile;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public Lorrizprofile getLorrizprofile(){
		return lorrizprofile;
	}

	public String getResult(){
		return result;
	}
}