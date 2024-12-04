package com.ruru.routelorry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadDetails{

	@SerializedName("drop_point")
	private String dropPoint;

	@SerializedName("description")
	private String description;

	@SerializedName("hours_type")
	private String hoursType;

	@SerializedName("total_amt")
	private String totalAmt;

	@SerializedName("drop_state_id")
	private String dropStateId;

	@SerializedName("id")
	private String id;

	@SerializedName("material_name")
	private String materialName;

	@SerializedName("pickup_state")
	private String pickupState;

	@SerializedName("load_status")
	private String loadStatus;

	@SerializedName("amount")
	private String amount;

	@SerializedName("loader_img")
	private Object loaderImg;

	@SerializedName("pickup_point")
	private String pickupPoint;

	@SerializedName("drop_lng")
	private String dropLng;

	@SerializedName("weight")
	private String weight;

	@SerializedName("loader_mobile")
	private String loaderMobile;

	@SerializedName("vehicle_img")
	private String vehicleImg;

	@SerializedName("visible_hours")
	private String visibleHours;

	@SerializedName("drop_state")
	private String dropState;

	@SerializedName("pick_state_id")
	private String pickStateId;

	@SerializedName("amt_type")
	private String amtType;

	@SerializedName("pick_lat")
	private String pickLat;

	@SerializedName("drop_lat")
	private String dropLat;

	@SerializedName("loader_name")
	private String loaderName;

	@SerializedName("pick_lng")
	private String pickLng;

	@SerializedName("post_date")
	private String postDate;

	@SerializedName("vehicle_title")
	private String vehicleTitle;

	@SerializedName("svisible_hours")
	private int svisibleHours;

	@SerializedName("loader_rate")
	private String loaderRate;

	@SerializedName("offer_description")
	@Expose
	private String offerDescription;

	@SerializedName("offer_price")
	@Expose
	private String offerPrice;

	@SerializedName("offer_by")
	@Expose
	private String offerBy;

	@SerializedName("offer_type")
	@Expose
	private String offerType;

	@SerializedName("offer_total")
	@Expose
	private String offerTotal;

	@SerializedName("flow_id")
	@Expose
	private String flowId;

	@SerializedName("comment_reject")
	@Expose
	private String commentReject;

	@SerializedName("wal_amt")
	@Expose
	private String walAmt;

	@SerializedName("pay_amt")
	@Expose
	private String payAmt;

	@SerializedName("Order_Transaction_id")
	@Expose
	private String orderTransactionId;

	@SerializedName("p_method_name")
	@Expose
	private String pMethodName;

	@SerializedName("is_rate")
	@Expose
	private String isRate;


	@SerializedName("uid")
	@Expose
	private String uid;


	@SerializedName("pick_name")
	@Expose
	private String pickName;

	@SerializedName("pick_mobile")
	@Expose
	private String pickMobile;

	@SerializedName("drop_name")
	@Expose
	private String dropName;

	@SerializedName("drop_mobile")
	@Expose
	private String dropMobile;


	public String getDropPoint(){
		return dropPoint;
	}

	public String getDescription(){
		return description;
	}

	public String getHoursType(){
		return hoursType;
	}

	public String getTotalAmt(){
		return totalAmt;
	}

	public String getDropStateId(){
		return dropStateId;
	}

	public String getId(){
		return id;
	}

	public String getMaterialName(){
		return materialName;
	}

	public String getPickupState(){
		return pickupState;
	}

	public String getLoadStatus(){
		return loadStatus;
	}

	public String getAmount(){
		return amount;
	}

	public Object getLoaderImg(){
		return loaderImg;
	}

	public String getPickupPoint(){
		return pickupPoint;
	}

	public String getDropLng(){
		return dropLng;
	}

	public String getWeight(){
		return weight;
	}

	public String getLoaderMobile(){
		return loaderMobile;
	}

	public String getVehicleImg(){
		return vehicleImg;
	}

	public String getVisibleHours(){
		return visibleHours;
	}

	public String getDropState(){
		return dropState;
	}

	public String getPickStateId(){
		return pickStateId;
	}

	public String getAmtType(){
		return amtType;
	}

	public String getPickLat(){
		return pickLat;
	}

	public String getDropLat(){
		return dropLat;
	}

	public String getLoaderName(){
		return loaderName;
	}

	public String getPickLng(){
		return pickLng;
	}

	public String getPostDate(){
		return postDate;
	}

	public String getVehicleTitle(){
		return vehicleTitle;
	}

	public int getSvisibleHours(){
		return svisibleHours;
	}

	public String getLoaderRate() {
		return loaderRate;
	}

	public void setLoaderRate(String loaderRate) {
		this.loaderRate = loaderRate;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public String getOfferPrice() {
		return offerPrice;
	}

	public String getOfferBy() {
		return offerBy;
	}

	public String getOfferType() {
		return offerType;
	}

	public String getOfferTotal() {
		return offerTotal;
	}

	public String getFlowId() {
		return flowId;
	}

	public String getCommentReject() {
		return commentReject;
	}

	public String getWalAmt() {
		return walAmt;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public String getOrderTransactionId() {
		return orderTransactionId;
	}

	public String getpMethodName() {
		return pMethodName;
	}

	public String getIsRate() {
		return isRate;
	}

	public String getUid() {
		return uid;
	}

	public String getPickName() {
		return pickName;
	}

	public String getPickMobile() {
		return pickMobile;
	}

	public String getDropName() {
		return dropName;
	}

	public String getDropMobile() {
		return dropMobile;
	}
}