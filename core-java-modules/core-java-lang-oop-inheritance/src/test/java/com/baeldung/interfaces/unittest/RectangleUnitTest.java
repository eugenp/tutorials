package com.baeldung.interfaces.unittest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RectangleUnitTest {

    @Test
    void givenRectangleInstance_thenAreaIsCalculated() {
        Shape rectangle = new Rectangle(5,4);
        double area = rectangle.area();
        assertEquals(20,area);
    }

    @Test
    void givenRectangleInstance_thenPerimeterIsCalculated() {
        Rectangle rectangle = new Rectangle(5,4);
        double perimeter = rectangle.perimeter();
        assertEquals(18,perimeter);
    }
}