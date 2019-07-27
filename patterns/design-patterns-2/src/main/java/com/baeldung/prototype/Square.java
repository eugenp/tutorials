package com.baeldung.prototype;

public class Square extends Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public Square(Square that) {
        this.side = that.side;
    }

    @Override
    public Square clone() {
        return new Square(this);
    }
}
