package com.baeldung.copyobjects;

public class Person implements Cloneable{
	private String name;
	private Address address;	
	
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

	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
}
