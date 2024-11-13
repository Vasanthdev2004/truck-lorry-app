package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class StatelistItem {

	@SerializedName("total_load")
	private int totalLoad;

	@SerializedName("img")
	private String img;

	@SerializedName("id")
	private String id;

	@SerializedName("total_lorry")
	private int totalLorry;

	@SerializedName("title")
	private String title;

	public int getTotalLoad(){
		return totalLoad;
	}

	public String getImg(){
		return img;
	}

	public String getId(){
		return id;
	}

	public int getTotalLorry(){
		return totalLorry;
	}

	public String getTitle(){
		return title;
	}
}