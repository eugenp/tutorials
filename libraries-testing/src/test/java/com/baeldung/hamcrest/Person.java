package com.baeldung.hamcrest;

public class Person {
	String name;
	String address;

	public Person(String personName, String personAddress) {
		name = personName;
		address = personAddress;
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

	@Override
	public String toString() {
		String str="[address:"+address+",name:"+name+"]";
		return str;
	}
	


	
}
