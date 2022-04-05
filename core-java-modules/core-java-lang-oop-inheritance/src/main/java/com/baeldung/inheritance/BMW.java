package com.baeldung.inheritance;

public class BMW extends Car {
    public BMW() {
    	super(5, "BMW");
    }
	
	@Override
    public String toString() {
    	return model;
    }
}
