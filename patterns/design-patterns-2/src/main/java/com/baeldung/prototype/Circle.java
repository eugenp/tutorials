package com.baeldung.prototype;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Shape {
    private int radius;
    private final List<String> metadata;

    public Circle(int radius, final List<String> metadata) {
        this.radius = radius;
        this.metadata = new ArrayList<>(metadata);
    }

    public Circle(Circle that) {
        this.radius = that.radius;
        this.metadata = new ArrayList<>(that.metadata);
    }

    public int getRadius() {
        return radius;
    }

    public List<String> getMetadata() {
        return new ArrayList<>(metadata);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public Circle clone() {
        return new Circle(this);
    }
}
