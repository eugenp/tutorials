package com.concretepage.bean;

public class Address {
	private String city;

	public Address(String city) {
		this.city = city;
		System.out.println("My city:" + city);
	}

	public String getCity() {
		return city;
	}
}

/**
*  Request Scope 每次request到来时 都会创建新的Bean
*/