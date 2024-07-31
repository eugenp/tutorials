package com.baeldung.powerinsteadofmathpow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindPowerInsteadOfUsingMathPowUnitTest {
    double result = 1;
    double base = 2;
    int exponent = 3;

    @Test
    public void givenBaseAndExponentNumbers_whenUtilizingIterativeApproach_thenReturnThePower() {

        for (int i = 0; i < exponent; i++) {
            result *= base;
        }

        assertEquals(8, result);
    }

    @Test
    public void givenBaseAndExponentNumbers_whenUtilizingRecursionApproach_thenReturnThePower() {

        result = calculatePowerRecursively(base, exponent);

        assertEquals(8, result);
    }

    private double calculatePowerRecursively(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        } else {
            return base * calculatePowerRecursively(base, exponent - 1);
        }
    }

    @Test
    public void givenBaseAndExponentNumbers_whenUtilizingFastApproach_thenReturnThePower() {
        result = calculatePowerFast(base, exponent);

        assertEquals(8, result);
    }

    private double calculatePowerFast(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        double halfPower = calculatePowerFast(base, exponent / 2);
        if (exponent % 2 == 0) {
            return halfPower * halfPower;
        } else {
            return base * halfPower * halfPower;
        }
    }
}
