package com.baeldung.inheritance;

/**
 * 宝马
 */
public class BMW extends Car {
    public BMW() {
    	super(5, "BMW");
    }
	
	@Override
    public String toString() {
    	return model;
    }
}
