package com.baeldung.copyobjects;

public class Employee {

	String name;
	Address address;

	public Employee(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}

	// copy constructor for shallow copy

	public Employee(Employee emp) {
		this.name = emp.name;
		this.address = emp.address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// standard constructors, getters and setters
	
}
