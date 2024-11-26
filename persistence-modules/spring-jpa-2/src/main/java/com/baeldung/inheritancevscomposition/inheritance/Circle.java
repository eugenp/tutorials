package com.baeldung.inheritancevscomposition.inheritance;

import jakarta.persistence.Entity;

@Entity
public class Circle extends Shape {
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}