package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class VehicleDataItem{

	@SerializedName("img")
	private String img;

	@SerializedName("max_weight")
	private String maxWeight;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("min_weight")
	private String minWeight;

	@SerializedName("status")
	private String status;

	public String getImg(){
		return img;
	}

	public String getMaxWeight(){
		return maxWeight;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getMinWeight(){
		return minWeight;
	}

	public String getStatus(){
		return status;
	}
}