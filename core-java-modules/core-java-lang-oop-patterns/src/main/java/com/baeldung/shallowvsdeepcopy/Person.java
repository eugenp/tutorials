package com.baeldung.shallowvsdeepcopy;

public class Person {

	private String name;
	private Address address;
	
    // Copy constructor for deep copy
    public Person(Person that) {
        this(that.getName(), new Address(that.getAddress()));
    }
    
	public Person(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}

	public Person(String name) {
		super();
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
