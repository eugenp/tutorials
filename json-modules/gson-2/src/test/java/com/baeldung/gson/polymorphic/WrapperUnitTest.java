package com.baeldung.gson.polymorphic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WrapperUnitTest {
    @Test
    void testSerializeWrapper() {
        List<Wrapper> shapes = Arrays.asList(
                new Wrapper(new Circle(4d)),
                new Wrapper(new Square(5d))
        );

        Gson gson = new Gson();
        String json = gson.toJson(shapes);

        assertEquals("[" +
            "{" +
                "\"circle\":{" +
                    "\"radius\":4.0," +
                    "\"area\":50.26548245743669" +
                "}" +
            "},{" +
                "\"square\":{" +
                    "\"side\":5.0," +
                    "\"area\":25.0" +
                "}" +
            "}]", json);
    }

    @Test
    void testDeserializeWrapper() {
        List<Wrapper> shapes = Arrays.asList(
                new Wrapper(new Circle(4d)),
                new Wrapper(new Square(5d))
        );

        Gson gson = new Gson();
        String json = gson.toJson(shapes);

        Type collectionType = new TypeToken<List<Wrapper>>(){}.getType();
        List<Wrapper> result = gson.fromJson(json, collectionType);

        assertEquals(shapes, result);
    }

    private static class Square implements Shape {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Square square = (Square) o;
            return Double.compare(square.side, side) == 0 && Double.compare(square.area, area) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(side, area);
        }
    }

    private static class Circle implements Shape {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Circle circle = (Circle) o;
            return Double.compare(circle.radius, radius) == 0 && Double.compare(circle.area, area) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(radius, area);
        }
    }

    private static class Wrapper {
        private final Circle circle;
        private final Square square;

        public Wrapper(Circle circle) {
            this.circle = circle;
            this.square = null;
        }

        public Wrapper(Square square) {
            this.square = square;
            this.circle = null;
        }

        public Circle getCircle() {
            return circle;
        }

        public Square getSquare() {
            return square;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper wrapper = (Wrapper) o;
            return Objects.equals(circle, wrapper.circle) && Objects.equals(square, wrapper.square);
        }

        @Override
        public int hashCode() {
            return Objects.hash(circle, square);
        }
    }

}
