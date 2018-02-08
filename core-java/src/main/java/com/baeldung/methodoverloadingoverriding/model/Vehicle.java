package com.baeldung.methodoverridingoverloading.model;

public class Vehicle {
    
    public String accelerate(long mph) {
        return "The vehicle accelerates at the following speed: " + mph + " MPH.";
    }
    
    public String stop() {
        return "The vehicle has stopped.";
    }
    
    public String run() {
        return "The vehicle is running.";
    }
}
