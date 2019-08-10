package com.baeldung.prototype;

import java.util.ArrayList;
import java.util.List;

public class Square extends Shape {
    private int side;
    private final List<String> metadata;

    public Square(int side, final List<String> metadata) {
        this.side = side;
        this.metadata = new ArrayList<>(metadata);
    }

    public Square(Square that) {
        this.side = that.side;
        this.metadata = new ArrayList<>(that.metadata);
    }

    public int getSide() {
        return side;
    }

    public List<String> getMetadata() {
        return new ArrayList<>(metadata);
    }

    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public Square clone() {
        return new Square(this);
    }
}
