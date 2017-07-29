package com.baeldung.jooby.bean;

public class Employee {

	String id;
	String name;
	String email;
	String phone;
	String address;

	public Employee(String id, String name, String email, String phone, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

}
