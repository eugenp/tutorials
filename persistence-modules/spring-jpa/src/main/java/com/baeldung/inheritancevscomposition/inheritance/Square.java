package com.baeldung.inheritancevscomposition.inheritance;

import jakarta.persistence.Entity;

@Entity
public class Square extends Shape {


    private double sideLength;
    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }
}
