package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyLoad{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("LoadHistoryData")
	private List<LoadHistoryDataItem> loadHistoryData;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public List<LoadHistoryDataItem> getLoadHistoryData(){
		return loadHistoryData;
	}

	public String getResult(){
		return result;
	}
}