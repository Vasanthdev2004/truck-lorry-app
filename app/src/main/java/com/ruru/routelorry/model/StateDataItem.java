package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

public class StateDataItem{

	@SerializedName("img")
	private String img;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("status")
	private String status;

	private boolean isSelect;

	public String getImg(){
		return img;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getStatus(){
		return status;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}
}