package com.baeldung.prototype;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PrototypeIntegrationTest {

    private ShapeCache shapeCache;

    @Before
    public void init() {
        Map<String, Shape> shapeMap = new HashMap<>();

        List<String> circleMetadata = new ArrayList<>();
        circleMetadata.add("Circle created by Author at XYZ time");
        shapeMap.put(Circle.class.getName(), new Circle(0, circleMetadata));

        List<String> squareMetadata = new ArrayList<>();
        squareMetadata.add("Square created by Author at XYZ time");
        shapeMap.put(Square.class.getName(), new Square(0, squareMetadata));

        shapeCache = new ShapeCache(shapeMap);
    }

    @Test
    public void givenCircleObjectWhenCloneIsInvokedThenReturnClonedObject() {
        Circle circle = (Circle) shapeCache.getShape(Circle.class.getName());
        circle.setRadius(10);

        assertTrue(circle.getRadius() != 0);
    }

    @Test
    public void givenSquareObjectWhenCloneIsInvokedThenReturnClonedObject() {
        Square square = (Square) shapeCache.getShape(Square.class.getName());
        square.setSide(10);

        assertTrue(square.getSide() != 0);
    }

}
