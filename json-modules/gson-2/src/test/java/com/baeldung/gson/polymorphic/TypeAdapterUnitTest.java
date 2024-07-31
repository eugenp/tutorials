package com.baeldung.gson.polymorphic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeAdapterUnitTest {
    @Test
    void testSerialize() {
        List<Shape> shapes = Arrays.asList(
                new Circle(4d),
                new Square(5d)
        );

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter());
        Gson gson = builder.create();

        String json = gson.toJson(shapes);

        assertEquals("[" +
                "{" +
                    "\"radius\":4.0," +
                    "\"area\":50.26548245743669," +
                    "\"type\":\"com.baeldung.gson.polymorphic.TypeAdapterUnitTest$Circle\"" +
                "},{" +
                    "\"side\":5.0," +
                    "\"area\":25.0," +
                    "\"type\":\"com.baeldung.gson.polymorphic.TypeAdapterUnitTest$Square\"" +
                "}]", json);
    }


    @Test
    void testDeserializeWrapper() {
        List<Shape> shapes = Arrays.asList(
                new Circle(4d),
                new Square(5d)
        );

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter());
        Gson gson = builder.create();

        String json = gson.toJson(shapes);

        Type collectionType = new TypeToken<List<Shape>>(){}.getType();
        List<Shape> result = gson.fromJson(json, collectionType);

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
}
