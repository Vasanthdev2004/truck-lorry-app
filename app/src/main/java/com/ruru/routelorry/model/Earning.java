package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

public class Earning{

	@SerializedName("withdraw_limit")
	private String withdrawLimit;

	@SerializedName("earning")
	private String earninga;

	public String getWithdrawLimit(){
		return withdrawLimit;
	}

	public String getEarninga(){
		return earninga;
	}
}