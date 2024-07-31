package com.baeldung.math.pointbetweentwopoints;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointLiesBetweenTwoPointsUnitTest {

    @Test
    public void givenAPoint_whenUsingDistanceFormula_thenCheckItLiesBetweenTwoPoints() {
        double x1 = 1, y1 = 1;
        double x2 = 5, y2 = 5;
        double x = 3, y = 3;
        assertTrue(PointLiesBetweenTwoPoints.findUsingDistanceFormula(x1, y1, x2, y2, x, y));
    }

    @Test
    public void givenAPoint_whenUsingSlopeFormula_thenCheckItLiesBetweenTwoPoints() {
        double x1 = 1, y1 = 1;
        double x2 = 5, y2 = 5;
        double x = 3, y = 3;
        assertTrue(PointLiesBetweenTwoPoints.findUsingSlopeFormula(x1, y1, x2, y2, x, y));
    }
}
