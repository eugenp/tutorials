package com.baeldung;

public class Person {

	private String name;
	private String city;
	
	public Person(String name, String city) {
	this.name=name;
	this.city=city;
	}
	public String getName() {
		return name;
	}
	
	public String getCity() {
		return city;
	}
	
	public String toString(){
		return "Name is "+name+ " and City is "+city;
	}
}
