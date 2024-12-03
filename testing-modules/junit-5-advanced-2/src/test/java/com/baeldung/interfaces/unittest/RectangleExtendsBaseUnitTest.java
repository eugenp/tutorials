package com.baeldung.interfaces.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class RectangleExtendsBaseUnitTest extends ShapeUnitTest {

    @Override
    public Map<String, Object> instantiateShapeWithExpectedArea() {
        Map<String, Object> shapeAreaMap = new HashMap<>();
        shapeAreaMap.put("shape", new Rectangle(5, 4));
        shapeAreaMap.put("area", 20.0);
        return shapeAreaMap;
    }

    @Test
    void whenPerimeterIsCalculated_thenSuccessful() {
        Rectangle rectangle = new Rectangle(5, 4);
        double perimeter = rectangle.perimeter();
        assertEquals(18, perimeter);
    }
}