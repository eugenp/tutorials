package com.baeldung.shallowvsdeepcopy;

public class Person {

	private String name;
	
    // Copy constructor for deep copy
    public Person(Person other) {
        this.name = other.name; 
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
