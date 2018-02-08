package com.baeldung.methodoverridingoverloading.model;

public class Car extends Vehicle {

    @Override
    public String accelerate(long mph) {
        return "The car accelerates at the following speed: " + mph + " MPH.";
    }
}
