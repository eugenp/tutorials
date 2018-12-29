package com.baeldung.interfaces.polymorphysim;

import java.util.ArrayList;
import java.util.List;

public class MainTestClass {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        Shape circleShape = new Circle(2);
        Shape squareShape = new Square(2);

        shapes.add(circleShape);
        shapes.add(squareShape);

        for (Shape shape : shapes) {
            System.out.println(shape.name() + " area: " + shape.area());
        }
    }
}
