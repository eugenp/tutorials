package com.baeldung.algorithms.gradientdescent;

import static org.junit.Assert.assertTrue;

import java.util.function.Function;

import org.junit.Test;

public class GradientDescentUnitTest {

    @Test
    public void givenFunction_whenStartingPointIsOne_thenLocalMinimumIsFound() {
        Function<Double, Double> df = x ->
            StrictMath.abs(StrictMath.pow(x, 3)) - (3 * StrictMath.pow(x, 2)) + x;
        GradientDescent gd = new GradientDescent();
        double res = gd.findLocalMinimum(df, 1);
        assertTrue(res > 1.78);
        assertTrue(res < 1.84);
    }
}
