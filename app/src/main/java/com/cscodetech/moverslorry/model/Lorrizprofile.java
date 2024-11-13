package com.cscodetech.moverslorry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lorrizprofile{

	@SerializedName("pro_pic")
	private String proPic;

	@SerializedName("rdate")
	private String rdate;

	@SerializedName("review")
	private String review;

	@SerializedName("name")
	private String name;

	@SerializedName("total_review")
	private int totalReview;

	@SerializedName("total_review_user_wise")
	private List<TotalReviewUserWiseItem> totalReviewUserWise;

	@SerializedName("total_lorry")
	private int totalLorry;

	@SerializedName("total_load")
	private int totalLoad;

	@SerializedName("total_routes")
	private List<String> totalRoutes;

	public String getProPic(){
		return proPic;
	}

	public String getRdate(){
		return rdate;
	}

	public String getReview(){
		return review;
	}

	public String getName(){
		return name;
	}

	public int getTotalReview(){
		return totalReview;
	}

	public List<TotalReviewUserWiseItem> getTotalReviewUserWise(){
		return totalReviewUserWise;
	}

	public int getTotalLorry(){
		return totalLorry;
	}

	public List<String> getTotalRoutes(){
		return totalRoutes;
	}

	public int getTotalLoad() {
		return totalLoad;
	}
}