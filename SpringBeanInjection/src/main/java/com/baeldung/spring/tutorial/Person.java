package com.baeldung.spring.tutorial;

public class Person {
	
	private int id;
	private String name;
	private int nationalId;
	private Address address;
	
	public Person() {
		
	}
	
	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void speak() {
		System.out.println("Hi! I'm a person. I can speak.");
	}

	public void setNationalId(int nationalId) {
		this.nationalId = nationalId;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", nationalId=" + nationalId
				+ ", address=" + address + "]";
	}

	
}
