package com.baeldung.copyobjects;

public class Student {

	int rollNum;
	String name;
	Address address;

	public Student(int rollNum, String name, Address address) {
		super();
		this.name = name;
		this.rollNum = rollNum;
		this.address = address;
	}

	// copy constructor for deep copy
	public Student(Student student) {
		this.name = student.name;
		this.rollNum = student.rollNum;
		this.address = new Address(student.address.houseNum,
				student.address.street, student.address.city,
				student.address.state);
	}

	public int getRollNum() {
		return rollNum;
	}

	public void setRollNum(int rollNum) {
		this.rollNum = rollNum;
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
