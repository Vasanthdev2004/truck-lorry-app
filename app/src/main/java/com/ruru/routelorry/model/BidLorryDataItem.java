package com.ruru.routelorry.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BidLorryDataItem implements Parcelable {

	@SerializedName("lorry_no")
	private String lorryNo;

	@SerializedName("routes")
	private String routes;

	@SerializedName("total_routes")
	private String totalRoutes[];

	@SerializedName("rc_verify")
	private String rcVerify;

	@SerializedName("lorry_title")
	private String lorryTitle;

	@SerializedName("id")
	private String id;

	@SerializedName("lorry_img")
	private String lorryImg;

	@SerializedName("description")
	private String description;

	@SerializedName("weight")
	private String weight;

	@SerializedName("is_verify")
	private String isCerify;


	protected BidLorryDataItem(Parcel in) {
		lorryNo = in.readString();
		routes = in.readString();
		totalRoutes = in.createStringArray();
		rcVerify = in.readString();
		lorryTitle = in.readString();
		id = in.readString();
		lorryImg = in.readString();
		description = in.readString();
		weight = in.readString();
		isCerify = in.readString();
	}

	public static final Creator<BidLorryDataItem> CREATOR = new Creator<BidLorryDataItem>() {
		@Override
		public BidLorryDataItem createFromParcel(Parcel in) {
			return new BidLorryDataItem(in);
		}

		@Override
		public BidLorryDataItem[] newArray(int size) {
			return new BidLorryDataItem[size];
		}
	};

	public String getLorryNo(){
		return lorryNo;
	}

	public String getRoutes(){
		return routes;
	}

	public String getRcVerify(){
		return rcVerify;
	}

	public String getLorryTitle(){
		return lorryTitle;
	}

	public String getId(){
		return id;
	}

	public String getLorryImg(){
		return lorryImg;
	}

	public String[] getTotalRoutes() {
		return totalRoutes;
	}

	public void setTotalRoutes(String[] totalRoutes) {
		this.totalRoutes = totalRoutes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getIsCerify() {
		return isCerify;
	}

	public void setIsCerify(String isCerify) {
		this.isCerify = isCerify;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(lorryNo);
		parcel.writeString(routes);
		parcel.writeStringArray(totalRoutes);
		parcel.writeString(rcVerify);
		parcel.writeString(lorryTitle);
		parcel.writeString(id);
		parcel.writeString(lorryImg);
		parcel.writeString(description);
		parcel.writeString(weight);
		parcel.writeString(isCerify);
	}
}