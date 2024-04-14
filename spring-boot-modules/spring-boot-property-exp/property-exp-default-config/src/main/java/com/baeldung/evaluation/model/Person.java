package com.baeldung.evaluation.model;

public class Person implements Cloneable {
	
	private int age; 
	private Address address;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	@Override
	public Object clone () throws CloneNotSupportedException {
		return super.clone();
	}
}