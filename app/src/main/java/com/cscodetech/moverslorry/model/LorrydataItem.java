package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class LorrydataItem {

	@SerializedName("lorry_no")
	private String lorryNo;

	@SerializedName("lorry_owner_id")
	private String lorryOwnerId;

	@SerializedName("routes_count")
	private int routesCount;

	@SerializedName("weight")
	private String weight;

	@SerializedName("rc_verify")
	private String rcVerify;

	@SerializedName("lorry_title")
	private String lorryTitle;

	@SerializedName("lorry_img")
	private String lorryImg;

	@SerializedName("lorry_id")
	private String lorryId;

	@SerializedName("lorry_owner_title")
	private String lorryOwnerTitle;

	@SerializedName("lorry_owner_img")
	private String lorryOwnerImg;

	@SerializedName("curr_location")
	private String currLocation;

	@SerializedName("vehicle_id")
	private String vehicleId;

	@SerializedName("loader_id")
	private String loaderId;

	@SerializedName("pickup_point")
	private String pickupPoint;

	@SerializedName("drop_point")
	private String dropPoint;

	@SerializedName("review")
	private String review;

	@SerializedName("load_distance")
	private String loadDistance;

	public String getLorryNo(){
		return lorryNo;
	}

	public String getLorryOwnerId(){
		return lorryOwnerId;
	}

	public int getRoutesCount(){
		return routesCount;
	}

	public String getWeight(){
		return weight;
	}

	public String getRcVerify(){
		return rcVerify;
	}

	public String getLorryTitle(){
		return lorryTitle;
	}

	public String getLorryImg(){
		return lorryImg;
	}

	public String getLorryId(){
		return lorryId;
	}

	public String getLorryOwnerTitle() {
		return lorryOwnerTitle;
	}

	public String getLorryOwnerImg() {
		return lorryOwnerImg;
	}

	public String getCurrLocation() {
		return currLocation;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getLoaderId() {
		return loaderId;
	}

	public String getPickupPoint() {
		return pickupPoint;
	}

	public String getDropPoint() {
		return dropPoint;
	}

	public String getReview() {
		return review;
	}

	public String getLoadDistance() {
		return loadDistance;
	}
}