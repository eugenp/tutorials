package com.baeldung.interfaces.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CircleUnitTest {

    @Test
    void whenAreaIsCalculated_thenSuccessful() {
        Shape circle = new Circle(5);
        double area = circle.area();
        assertEquals(78.5, area);
    }

    @Test
    void whenCircumferenceIsCalculated_thenSuccessful() {
        Circle circle = new Circle(2);
        double circumference = circle.circumference();
        assertEquals(12.56, circumference);
    }
}