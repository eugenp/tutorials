package com.baeldung.comparedouble;

import com.google.common.math.DoubleMath;

import org.apache.commons.math3.util.Precision;
import org.junit.Test;

import static com.baeldung.comparedouble.DoubleComparator.areEqual;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompareDoubleUnitTest {

    @Test
    public void givenDoubleValuesThatShouldHaveSameValue_whenUsingSimpleComparison_thenFails() {
        double d1 = getFirstDouble(0);

        double d2 = .1 * 8;

        assertThat(d1 == d2).isFalse();
    }

    @Test
    public void givenDoubleValuesThatShouldHaveSameValue_whenUsingThresholdComparison_thenSuccess() {
        double d1 = getFirstDouble(0);

        double d2 = .1 * 8;

        double epsilon = 0.000001d;

        assertThat(Math.abs(d1 - d2) < epsilon).isTrue();
    }

    @Test
    public void givenDoubleValuesThatShouldHaveSameValue_whenUsingGuavaFuzzyComparison_thenSuccess() {
        double d1 = getFirstDouble(0);
        double d2 = .1 * 8;

        double epsilon = 0.000001d;

        assertThat(DoubleMath.fuzzyEquals(d1, d2, epsilon)).isTrue();
    }

    @Test
    public void givenDoubleValuesThatShouldHaveSameValue_whenUsingCommonsMathComparison_thenSuccess() {
        double d1 = getFirstDouble(0);
        double d2 = .1 * 8;

        double epsilon = 0.000001d;

        assertThat(Precision.equals(d1, d2, epsilon)).isTrue();
        assertThat(Precision.equals(d1, d2)).isTrue();
    }

    @Test
    public void givenDoubleValuesThatShouldHaveSameValue_whenUsingJunitComparison_thenSuccess() {
        double d1 = getFirstDouble(0);
        double d2 = .1 * 8;

        double epsilon = 0.000001d;

        assertEquals(d1, d2, epsilon);
    }

    private double getFirstDouble(double d1) {
        for (int i = 1; i <= 8; i++) {
            d1 += .1;
        }
        return d1;
    }

    @Test
    public void givenTwoEqualDoubleValues_whenUseComparator_thenReturnsZero() {
        double d1 = getFirstDouble(0);
        double d2 = .1 * 8;
        DoubleComparator comparator = new DoubleComparator(0.000001d);
        int result = comparator.compare(d1, d2);
        assertEquals(0, result);
    }

    @Test
    public void givenTwoDoubleValues_whenUseSetScale_thenReturnAsExpected() {
        double d1 = 0.7999999999999999;
        double d2 = 0.8;

        assertTrue(areEqual(d1, d2, 1), "Should be equal up to 1 decimal place");
        assertTrue(areEqual(d1, d2, 2), "Should be equal up to 2 decimal places");
    }
}