package com.baeldung.gson.polymorphic;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeFieldUnitTest {
    @Test
    void testSerialize() {
        List<Shape> shapes = Arrays.asList(
                new Circle(4d),
                new Square(5d)
        );

        Gson gson = new Gson();
        String json = gson.toJson(shapes);

        assertEquals("[" +
                "{" +
                    "\"type\":\"circle\"," +
                    "\"radius\":4.0," +
                    "\"area\":50.26548245743669" +
                "},{" +
                    "\"type\":\"square\"," +
                    "\"side\":5.0," +
                    "\"area\":25.0" +
                "}]", json);
    }

    private static class Square implements Shape {
        private final String type = "square";
        private final double side;
        private final double area;

        public Square(double side) {
            this.side = side;
            this.area = side * side;
        }

        @Override
        public double getArea() {
            return area;
        }
    }

    private static class Circle implements Shape {
        private final String type = "circle";
        private final double radius;

        private final double area;

        public Circle(double radius) {
            this.radius = radius;
            this.area = Math.PI * radius * radius;
        }

        @Override
        public double getArea() {
            return area;
        }
    }

}
