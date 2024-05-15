package com.baeldung.interfaces.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RectangleUnitTest {

    @Test
    void whenAreaIsCalculated_thenSuccessful() {
        Shape rectangle = new Rectangle(5, 4);
        double area = rectangle.area();
        assertEquals(20, area);
    }

    @Test
    void whenPerimeterIsCalculated_thenSuccessful() {
        Rectangle rectangle = new Rectangle(5, 4);
        double perimeter = rectangle.perimeter();
        assertEquals(18, perimeter);
    }
}