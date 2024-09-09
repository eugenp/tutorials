package com.baeldung.shallowvsdeepcopy;

public class Person {

	private String name;
	
    // Copy constructor for deep copy
    public Person(Person other) {
        this.name = other.name; // Primitive type, so just copy the value
    }
    
	public Person(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
