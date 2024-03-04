package com.baeldung.algorithms.perfectnumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerfectNumberUnitTest {

    @Test
    void testIsPerfectBruteForce() {
        // Test a perfect number
        assertTrue(PerfectNumber.isPerfectBruteForce(6));

        // Test a non-perfect number
        assertFalse(PerfectNumber.isPerfectBruteForce(10));

        // Test a negative number
        assertFalse(PerfectNumber.isPerfectBruteForce(-28));
    }

    @Test
    void testIsPerfectEuclidEuler() {
        // Test a perfect number
        assertTrue(PerfectNumber.isPerfectEuclidEuler(28));

        // Test a non-perfect number
        assertFalse(PerfectNumber.isPerfectEuclidEuler(10));

        // Test a negative number
        assertFalse(PerfectNumber.isPerfectEuclidEuler(-6));
    }
}