package com.baeldung.methodoverloadingoverriding.model;

/**
 * @author zn.wang
 */
public class Car extends Vehicle {

    @Override
    public String accelerate(long mph) {
        return "The car accelerates at : " + mph + " MPH.";
    }
}
