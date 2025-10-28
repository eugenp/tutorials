/**
 * Package to host JUnit Test code for AngleDifferenceCalculator Class
 */
package com.baeldung.algorithms.twoanglesdifference;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



class AngleDifferenceCalculatorTest {

    private static final double EPSILON = 0.0001;

    @Test
    void whenNormalizingAngle_thenReturnsCorrectRange() {
        assertEquals(90, AngleDifferenceCalculator.normalizeAngle(450), EPSILON);
        assertEquals(30, AngleDifferenceCalculator.normalizeAngle(390), EPSILON);
        assertEquals(330, AngleDifferenceCalculator.normalizeAngle(-30), EPSILON);
        assertEquals(0, AngleDifferenceCalculator.normalizeAngle(360), EPSILON);
    }

    @Test
    void whenCalculatingAbsoluteDifference_thenReturnsCorrectValue() {
        assertEquals(100, AngleDifferenceCalculator.absoluteDifference(10, 110), EPSILON);
        assertEquals(290, AngleDifferenceCalculator.absoluteDifference(10, 300), EPSILON);
        assertEquals(30, AngleDifferenceCalculator.absoluteDifference(-30, 0), EPSILON);
    }

    @Test
    void whenCalculatingShortestDifference_thenReturnsCorrectValue() {
        assertEquals(100, AngleDifferenceCalculator.shortestDifference(10, 110), EPSILON);
        assertEquals(70, AngleDifferenceCalculator.shortestDifference(10, 300), EPSILON);
        assertEquals(30, AngleDifferenceCalculator.shortestDifference(-30, 0), EPSILON);
        assertEquals(0, AngleDifferenceCalculator.shortestDifference(360, 0), EPSILON);
    }

    @Test
    void whenCalculatingSignedShortestDifference_thenReturnsCorrectValue() {
        assertEquals(100, AngleDifferenceCalculator.signedShortestDifference(10, 110), EPSILON);
        assertEquals(-70, AngleDifferenceCalculator.signedShortestDifference(10, 300), EPSILON);
        assertEquals(30, AngleDifferenceCalculator.signedShortestDifference(-30, 0), EPSILON);
        assertEquals(70, AngleDifferenceCalculator.signedShortestDifference(300, 10), EPSILON);
    }
}

