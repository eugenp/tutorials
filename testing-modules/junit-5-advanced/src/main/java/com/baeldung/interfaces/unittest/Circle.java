package com.baeldung.interfaces.unittest;

public class Circle implements Shape {

    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return 3.14 * radius * radius;
    }

    public double circumference() {
        return 2 * 3.14 * radius;
    }
}
