package com.baeldung.features;

import com.baeldung.features.JEP409.Circle;
import com.baeldung.features.JEP409.Shape;
import com.baeldung.features.JEP409.Triangle;

public class JEP406 {

    static record Human (String name, int age, String profession) {}

    public String checkObject(Object obj) {
        return switch (obj) {
            case Human h -> "Name: %s, age: %s and profession: %s".formatted(h.name(), h.age(), h.profession());
            case Circle c -> "This is a circle";
            case Shape s -> "It is just a shape";
            case null -> "It is null";
            default -> "It is an object";
        };
    }

    public String checkShape(Shape shape) {
        return switch (shape) {
            case Triangle t && (t.getNumberOfSides() != 3) -> "This is a weird triangle";
            case Circle c && (c.getNumberOfSides() != 0) -> "This is a weird circle";
            default -> "Just a normal shape";
        };
    }
}
