package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindLoadDataItem{

	@SerializedName("max_weight")
	private String maxWeight;

	@SerializedName("loaddata")
	private List<LoaddataItem> loaddata;

	@SerializedName("id")
	private String id;

	@SerializedName("total_lorry")
	private int totalLorry;

	@SerializedName("title")
	private String title;

	@SerializedName("min_weight")
	private String minWeight;

	public String getMaxWeight(){
		return maxWeight;
	}

	public List<LoaddataItem> getLoaddata(){
		return loaddata;
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

	public String getMinWeight(){
		return minWeight;
	}
}