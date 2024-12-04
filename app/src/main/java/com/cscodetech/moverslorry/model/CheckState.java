package com.ruru.routelorry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckState {


	@SerializedName("pick_state_id")
	@Expose
	private String currStateId;
	@SerializedName("drop_state_id")
	@Expose
	private String dropStateId;

	@SerializedName("ResponseCode")
	@Expose
	private String responseCode;
	@SerializedName("Result")
	@Expose
	private String result;
	@SerializedName("ResponseMsg")
	@Expose
	private String responseMsg;

	public String getCurrStateId() {
		return currStateId;
	}


	public void setCurrStateId(String currStateId) {
		this.currStateId = currStateId;
	}

	public String getDropStateId() {
		return dropStateId;
	}

	public void setDropStateId(String dropStateId) {
		this.dropStateId = dropStateId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

}