package com.baeldung.prototype;

import java.util.List;

public class Circle extends Shape {
    private int radius;
    private List<String> metadata;

    public Circle(int radius, List<String> metadata) {
        this.radius = radius;
        this.metadata = metadata;
    }

    public Circle(Circle that) {
        this.radius = that.radius;
        this.metadata = that.metadata;
    }

    @Override
    public Circle clone() {
        return new Circle(this);
    }
}
