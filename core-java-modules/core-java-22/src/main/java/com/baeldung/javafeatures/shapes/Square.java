package com.baeldung.javafeatures.shapes;

public class Square extends Shape {
    int sides;
    int length;

    public Square(int sides, int length) {
        if (sides != 4 && length <= 0) {
            throw new IllegalArgumentException("Cannot form Square");
        }
        super(sides, length);
    }

    @Override
    int getArea() {
        return 4 * length;
    }

    public String whoAmI() {
        return "I am a square";
    }
}
