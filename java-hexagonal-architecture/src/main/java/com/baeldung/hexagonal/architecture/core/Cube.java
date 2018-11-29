package com.baeldung.hexagonal.architecture.core;

public class Cube {

    private Double sideLength;

    public Cube(Double sideLength) {
        super();
        this.sideLength = sideLength;
    }

    public Double getSideLength() {
        return sideLength;
    }

    public void setSideLength(Double sideLength) {
        this.sideLength = sideLength;
    }
}
