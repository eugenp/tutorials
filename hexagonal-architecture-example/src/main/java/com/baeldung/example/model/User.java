package com.baeldung.example.model;

public class User {
	
	private int userId;
	
	private String fullName;
	
	private String phoneNo;
	
	private String address;
	
	public User() {
		super();
	}

	public User(int userId, String fullName, String phoneNo, String address) {
		this();
		this.userId = userId;
		this.fullName = fullName;
		this.phoneNo = phoneNo;
		this.address = address;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
