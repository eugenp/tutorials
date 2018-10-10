package com.baeldung.annotations;

import org.springframework.beans.factory.annotation.Value;

public class Engine {

    @Value("8")
    private int cylinderCount;

    @Value("${engine.fuelType}")
    private String fuelType;

    public Engine() {
        this(8);
    }

    public Engine(@Value("8") int cylinderCount) {
        this.cylinderCount = cylinderCount;
    }

    @Value("8")
    public void setCylinderCount(int cylinderCount) {
        this.cylinderCount = cylinderCount;
    }

}
