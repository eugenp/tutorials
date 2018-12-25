package com.baeldung.interfaces.polymorphysim;

import java.util.function.Predicate;

public class FunctionalMain {

public static void main(String[] args) {
    Shape circleShape = new Circle(2);
    Shape squareShape = new Square(2);

    DisplayShape DisplayShape = new DisplayShape();
    DisplayShape.add(circleShape);
    DisplayShape.add(squareShape);

    Predicate<Shape> checkArea = (shape) -> shape.area() < 5;

    for (Shape shape : DisplayShape.getShapes()) {
        if (checkArea.test(shape)) {
            System.out.println(shape.name() + " " + shape.area());
        }
    }
}
}
