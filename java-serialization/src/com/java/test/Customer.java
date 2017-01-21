package com.java.test;

import java.io.Serializable;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1234343434343434L;
	String id;
	String name;
	transient String address;
	static String designation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static String getDesignation() {
		return designation;
	}

	public static void setDesignation(String designation) {
		Customer.designation = designation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString() {
		return "Id:"+id+", Name:"+name+", Address:"+address+", Designation:"+designation;
	}
}
