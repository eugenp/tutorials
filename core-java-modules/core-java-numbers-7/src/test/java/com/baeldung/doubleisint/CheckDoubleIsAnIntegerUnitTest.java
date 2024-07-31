package com.baeldung.doubleisint;

import com.google.common.math.DoubleMath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckDoubleIsAnIntegerUnitTest {

    boolean notNaNOrInfinity(double d) {
        return !(Double.isNaN(d) || Double.isInfinite(d));
    }

    @Test
    void whenConvertingToInt_thenGetExpectedResult() {
        double d1 = 42.0D;
        boolean d1IsInteger = notNaNOrInfinity(d1) && (int) d1 == d1;
        assertTrue(d1IsInteger);

        double d2 = 42.42D;
        boolean d2IsInteger = notNaNOrInfinity(d2) && (int) d2 == d2;
        assertFalse(d2IsInteger);

        double d3 = 2.0D * Integer.MAX_VALUE;
        boolean d3IsInteger = notNaNOrInfinity(d3) && (int) d3 == d3;
        assertTrue(!d3IsInteger); // <-- fails if exceeding Integer's range
    }

    @Test
    void whenUsingModuloOperator_thenGetExpectedResult() {
        double d1 = 42.0D;
        boolean d1IsInteger = notNaNOrInfinity(d1) && (d1 % 1) == 0;
        assertTrue(d1IsInteger);

        double d2 = 42.42D;
        boolean d2IsInteger = notNaNOrInfinity(d2) && (d2 % 1) == 0;
        assertFalse(d2IsInteger);

        double d3 = 2.0D * Integer.MAX_VALUE;
        boolean d3IsInteger = notNaNOrInfinity(d3) && (d3 % 1) == 0;
        assertTrue(d3IsInteger);

    }


    @Test
    void whenCheckingFloorOrCeilingValue_thenGetExpectedResult() {
        double d1 = 42.0D;
        boolean d1IsInteger = notNaNOrInfinity(d1) && Math.floor(d1) == d1;
        assertTrue(d1IsInteger);

        double d2 = 42.42D;
        boolean d2IsInteger = notNaNOrInfinity(d2) && Math.floor(d2) == d2;
        assertFalse(d2IsInteger);

        double d3 = 2.0D * Integer.MAX_VALUE;
        boolean d3IsInteger = notNaNOrInfinity(d3) && Math.floor(d3) == d3;
        assertTrue(d3IsInteger);

    }

    @Test
    void whenUsingGuava_thenGetExpectedResult() {
        double d1 = 42.0D;
        boolean d1IsInteger = DoubleMath.isMathematicalInteger(d1);
        assertTrue(d1IsInteger);

        double d2 = 42.42D;
        boolean d2IsInteger = DoubleMath.isMathematicalInteger(d2);
        assertFalse(d2IsInteger);

        double d3 = 2.0D * Integer.MAX_VALUE;
        boolean d3IsInteger = DoubleMath.isMathematicalInteger(d3);
        assertTrue(d3IsInteger);

        boolean isInfinityInt = DoubleMath.isMathematicalInteger(Double.POSITIVE_INFINITY);
        assertFalse(isInfinityInt);

        boolean isNanInt = DoubleMath.isMathematicalInteger(Double.NaN);
        assertFalse(isNanInt);
    }
}