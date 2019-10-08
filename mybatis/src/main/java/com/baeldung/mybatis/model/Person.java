package com.baeldung.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private Integer personId;
	private String name;
	private List<Address> addresses;

	public Person() {
	}

	public Person(Integer personId, String name) {
		this.personId = personId;
		this.name = name;
		addresses = new ArrayList<Address>();
	}

	public Person(String name) {
		this.name = name;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String getName() {
		return name;
	}

	public void addAddress(Address address) {
		addresses.add(address);
	}

	public List<Address> getAddresses() {
		return addresses;
	}
}
