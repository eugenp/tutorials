package com.baeldung.javafeatures.shapes;

public class Circle extends Shape {
    int sides;
    int length;

    Circle(int sides, int length) {
        if (sides != 0 && length > 0) {
            throw new IllegalArgumentException("Cannot form circle");
        }
        super(sides, length);
    }

    @Override
    int getArea() {
        return (int) (this.length * this.length * 3.14);
    }

}