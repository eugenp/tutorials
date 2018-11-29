package com.baeldung.hexagonal.architecture.core;

public class Square {

    private Double sideLength;

    public Square(Double sideLength) {
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
