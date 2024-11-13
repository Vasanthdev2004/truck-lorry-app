package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class LoadDetailMain{

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("LoadDetails")
	private LoadDetails loadDetails;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public LoadDetails getLoadDetails(){
		return loadDetails;
	}

	public String getResult(){
		return result;
	}
}