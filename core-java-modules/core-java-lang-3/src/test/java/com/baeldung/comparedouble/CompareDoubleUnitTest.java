package com.baeldung.comparedouble;

import com.google.common.math.DoubleMath;
import org.apache.commons.math3.util.Precision;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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
}