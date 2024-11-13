package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class LoadHistoryDataItem{

	@SerializedName("drop_point")
	private String dropPoint;

	@SerializedName("total_amt")
	private String totalAmt;

	@SerializedName("amount")
	private String amount;

	@SerializedName("post_date")
	private String postDate;

	@SerializedName("pickup_point")
	private String pickupPoint;

	@SerializedName("vehicle_title")
	private String vehicleTitle;

	@SerializedName("id")
	private String id;

	@SerializedName("vehicle_img")
	private String vehicleImg;

	@SerializedName("drop_state")
	private String dropState;

	@SerializedName("amt_type")
	private String amtType;

	@SerializedName("pickup_state")
	private String pickupState;

	@SerializedName("load_status")
	private String loadStatus;

	@SerializedName("load_distance")
	private String loadDistance;

	public String getDropPoint(){
		return dropPoint;
	}

	public String getTotalAmt(){
		return totalAmt;
	}

	public String getAmount(){
		return amount;
	}

	public String getPostDate(){
		return postDate;
	}

	public String getPickupPoint(){
		return pickupPoint;
	}

	public String getVehicleTitle(){
		return vehicleTitle;
	}

	public String getId(){
		return id;
	}

	public String getVehicleImg(){
		return vehicleImg;
	}

	public String getDropState(){
		return dropState;
	}

	public String getAmtType(){
		return amtType;
	}

	public String getPickupState(){
		return pickupState;
	}

	public String getLoadStatus(){
		return loadStatus;
	}

	public String getLoadDistance() {
		return loadDistance;
	}
}