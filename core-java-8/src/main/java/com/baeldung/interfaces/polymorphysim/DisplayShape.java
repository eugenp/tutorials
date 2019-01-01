package com.baeldung.interfaces.polymorphysim;

import java.util.ArrayList;

public class DisplayShape {

    private ArrayList<Shape> shapes;

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public DisplayShape() {
        shapes = new ArrayList<>();
    }

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void display() {
        for (Shape shape : shapes) {
            System.out.println(shape.name() + " area: " + shape.area());
        }
    }
}
