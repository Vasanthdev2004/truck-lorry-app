package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class Home{

	@SerializedName("HomeData")
	private HomeData homeData;

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;



	public HomeData getHomeData(){
		return homeData;
	}

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}


}