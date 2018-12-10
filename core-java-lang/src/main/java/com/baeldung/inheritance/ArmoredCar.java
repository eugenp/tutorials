package com.baeldung.inheritance;

public class ArmoredCar extends Car implements Floatable, Flyable{
    private int bulletProofWindows;
    private String model;
    
    public void remoteStartCar() {
        // this vehicle can be started by using a remote control
    }
    
    public String registerModel() {
        return model;
    }
    
    public String getAValue() {
        return super.model;   // returns value of model defined in base class Car
        // return this.model;   // will return value of model defined in ArmoredCar
        // return model;   // will return value of model defined in ArmoredCar
    }
    
    public static String msg() {
    	// return super.msg(); // this won't compile.
    	return "ArmoredCar"; 
    }
    
	@Override
    public void floatOnWater() {
        System.out.println("I can float!");
    }
    
	@Override
    public void fly() {
        System.out.println("I can fly!");
    }
    
    public void aMethod() {
        // System.out.println(duration); // Won't compile
        System.out.println(Floatable.duration); // outputs 10
        System.out.println(Flyable.duration); // outputs 20
    }
    
    
}
