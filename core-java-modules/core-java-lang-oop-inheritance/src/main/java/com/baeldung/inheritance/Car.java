package com.baeldung.inheritance;

public class Car {
	private final int DEFAULT_WHEEL_COUNT = 5;
	private final String DEFAULT_MODEL = "Basic";

	protected int wheels;
    protected String model;
    
    public Car() {
    	this.wheels = DEFAULT_WHEEL_COUNT;
    	this.model = DEFAULT_MODEL;
    }
    
    public Car(int wheels, String model) {
    	this.wheels = wheels;
    	this.model = model;
    }
        
    public void start() {
        // Check essential parts
        // If okay, start.
    }
    public static int count = 10;
    public static String msg() {
        return "Car";
    }
    
    public String toString() {
    	return model;
    } 
}