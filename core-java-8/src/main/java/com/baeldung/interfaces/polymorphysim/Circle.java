package com.baeldung.interfaces.polymorphysim;

public class Circle implements Shape {

    private double radius;

    public Circle(double radius){
        this.radius = radius;
    }

    @Override
    public String name() {
        return "Circle";
    }

    @Override
    public double area() {
        return Math.PI * (radius * radius);
    }

    @Override
    public String getColor() {
        return "green";
    }
}
