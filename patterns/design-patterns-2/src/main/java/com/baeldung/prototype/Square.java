package com.baeldung.prototype;

import java.util.List;

public class Square extends Shape {
    private int side;
    private List<String> metadata;

    public Square(int side, List<String> metadata) {
        this.side = side;
        this.metadata = metadata;
    }

    public Square(Square that) {
        this.side = that.side;
        this.metadata = that.metadata;
    }

    @Override
    public Square clone() {
        return new Square(this);
    }
}
