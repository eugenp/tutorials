package com.baeldung.multiple_bean_instantiation.solution1;

/*@Component*/
public class Person {
	private String firstName;
	private String lastName;
	public Person(String firstName, String secondName) {
		super();
		this.firstName = firstName;
		this.lastName = secondName;
	}
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", secondName=" + lastName + "]";
	}
	
}
