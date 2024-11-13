package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("StateData")
	private List<StateDataItem> stateData;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public List<StateDataItem> getStateData(){
		return stateData;
	}

	public String getResult(){
		return result;
	}
}