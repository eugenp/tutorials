package com.baeldung.interfaces.unittest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CircleUnitTest {

    @Test
    void givenCircleInstance_thenAreaIsCalculated() {
        Shape circle = new Circle(2);
        double area = circle.area();
        assertEquals(12.56,area);
    }

    @Test
    void givenCircleInstance_thenCircumferenceIsCalculated(){
        Circle circle = new Circle(2);
        double circumference = circle.circumference();
        assertEquals(12.56,circumference);
    }
}