package com.ruru.routelorry.model;

import com.google.gson.annotations.SerializedName;

public class UserLogin{

	@SerializedName("ccode")
	private String ccode;

	@SerializedName("password")
	private String password;

	@SerializedName("code")
	private String code;

	@SerializedName("wallet")
	private String wallet;

	@SerializedName("pro_pic")
	private Object proPic;

	@SerializedName("rdate")
	private String rdate;

	@SerializedName("refercode")
	private Object refercode;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public String getCcode(){
		return ccode;
	}

	public String getPassword(){
		return password;
	}

	public String getCode(){
		return code;
	}

	public String getWallet(){
		return wallet;
	}

	public Object getProPic(){
		return proPic;
	}

	public String getRdate(){
		return rdate;
	}

	public Object getRefercode(){
		return refercode;
	}

	public String getName(){
		return name;
	}

	public String getMobile(){
		return mobile;
	}

	public String getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getStatus(){
		return status;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}

	public void setProPic(Object proPic) {
		this.proPic = proPic;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public void setRefercode(Object refercode) {
		this.refercode = refercode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}