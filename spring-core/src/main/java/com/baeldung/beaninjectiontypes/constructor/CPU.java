package com.baeldung.beaninjectiontypes.constructor;

public class CPU {

    private String brand;
    private double speed;

    public CPU(String brand, double speed) {
        this.brand = brand;
        this.speed = speed;
    }
}
