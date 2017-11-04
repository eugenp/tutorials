package com.baeldung.model;

import org.springframework.beans.factory.annotation.Autowired;

public class User {
	
	private String uid;
	private String name;
	private UserAddress userAddress;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public UserAddress getUserAddress() {
		return this.userAddress;
	}
	
	//dependency injection by setting method
	@Autowired
	 public void setUserAddress(UserAddress userAddress) {
	    this.userAddress = userAddress;
	 }

}
