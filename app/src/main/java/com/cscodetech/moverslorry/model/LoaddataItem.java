package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

public class LoaddataItem{

	@SerializedName("drop_point")
	private String dropPoint;

	@SerializedName("amount")
	private String amount;

	@SerializedName("is_bid")
	private int isBid;

	@SerializedName("pickup_point")
	private String pickupPoint;

	@SerializedName("vehicle_img")
	private String vehicleImg;

	@SerializedName("drop_state")
	private String dropState;

	@SerializedName("amt_type")
	private String amtType;

	@SerializedName("total_amt")
	private String totalAmt;

	@SerializedName("post_date")
	private String postDate;

	@SerializedName("vehicle_title")
	private String vehicleTitle;

	@SerializedName("id")
	private String id;

	@SerializedName("pickup_state")
	private String pickupState;

	@SerializedName("load_status")
	private String loadStatus;

	@SerializedName("weight")
	private String weight;

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("owner_img")
	private String ownerImg;

	@SerializedName("owner_rating")
	private String ownerRating;

	@SerializedName("material_name")
	private String materialName;

	@SerializedName("bid_amount")
	private String bidAmount;

	@SerializedName("bid_amount_type")
	private String bidAmountType;

	@SerializedName("bid_total_amt")
	private String bidTotalAmt;

	@SerializedName("uid")
	private String uid;

	@SerializedName("load_distance")
	private String loadDistance;



	public String getDropPoint(){
		return dropPoint;
	}

	public String getAmount(){
		return amount;
	}

	public int getIsBid(){
		return isBid;
	}

	public String getPickupPoint(){
		return pickupPoint;
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

	public String getTotalAmt(){
		return totalAmt;
	}

	public String getPostDate(){
		return postDate;
	}

	public String getVehicleTitle(){
		return vehicleTitle;
	}

	public String getId(){
		return id;
	}

	public String getPickupState(){
		return pickupState;
	}

	public String getLoadStatus(){
		return loadStatus;
	}

	public String getWeight() {
		return weight;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getOwnerImg() {
		return ownerImg;
	}

	public String getOwnerRating() {
		return ownerRating;
	}

	public void setIsBid(int isBid) {
		this.isBid = isBid;
	}

	public String getMaterialName() {
		return materialName;
	}

	public String getBidAmount() {
		return bidAmount;
	}

	public String getBidAmountType() {
		return bidAmountType;
	}

	public String getBidTotalAmt() {
		return bidTotalAmt;
	}

	public String getUid() {
		return uid;
	}

	public String getLoadDistance() {
		return loadDistance;
	}
}