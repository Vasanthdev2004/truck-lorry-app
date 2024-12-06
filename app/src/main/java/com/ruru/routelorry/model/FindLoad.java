package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindLoad{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("FindLoadData")
	private List<FindLoadDataItem> findLoadData;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public List<FindLoadDataItem>  getFindLoadData(){
		return findLoadData;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}