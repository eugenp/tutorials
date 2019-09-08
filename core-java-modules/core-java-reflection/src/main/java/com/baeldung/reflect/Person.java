package com.baeldung.reflect;

public class Person {

	private String fullName;

	public Person(String fullName) {
		this.fullName = fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}
}
