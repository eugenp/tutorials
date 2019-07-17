package com.baeldung.interfaces.polymorphysim;

import java.util.ArrayList;
import java.util.List;

public class MainTestClass {

    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        Shape circleShape = new Circle();
        Shape squareShape = new Square();

        shapes.add(circleShape);
        shapes.add(squareShape);

        for (Shape shape : shapes) {
            System.out.println(shape.name());
        }
    }
}
