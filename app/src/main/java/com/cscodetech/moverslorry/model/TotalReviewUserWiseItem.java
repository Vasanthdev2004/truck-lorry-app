package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class TotalReviewUserWiseItem{

	@SerializedName("rate_text")
	private String rateText;

	@SerializedName("rate_number")
	private String rateNumber;

	@SerializedName("customername")
	private String customername;

	@SerializedName("user_img")
	private String userImg;

	public String getRateText(){
		return rateText;
	}

	public String getRateNumber(){
		return rateNumber;
	}

	public String getCustomername(){
		return customername;
	}

	public String getUserImg(){
		return userImg;
	}
}