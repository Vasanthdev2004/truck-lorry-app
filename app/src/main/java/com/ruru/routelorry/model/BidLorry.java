package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BidLorry{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("BidLorryData")
	private List<BidLorryDataItem> bidLorryData;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public List<BidLorryDataItem> getBidLorryData(){
		return bidLorryData;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}