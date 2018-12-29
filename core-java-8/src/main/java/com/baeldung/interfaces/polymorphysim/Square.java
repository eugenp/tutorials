package com.baeldung.interfaces.polymorphysim;

public class Square implements Shape {

    private double width;

    public Square(double width) {
        this.width = width;
    }

    @Override
    public String name() {
        return "Square";
    }

    @Override
    public double area() {
        return width * width;
    }

    @Override
    public String getColor() {
        return "red";
    }
}
