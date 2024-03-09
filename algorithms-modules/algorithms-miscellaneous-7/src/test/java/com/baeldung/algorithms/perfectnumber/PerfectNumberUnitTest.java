package com.baeldung.algorithms.perfectnumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerfectNumberUnitTest {
    @Test
    void testIsPerfectBruteForce() {
        // Test a perfect number
        givenNumberIsPerfect(6);

        // Test a non-perfect number
        givenNumberIsNotPerfect(10);

        // Test a negative number
        givenNegativeNumberIsNotPerfect(-28);
    }

    @Test
    void testIsPerfectStream() {
        // Test a perfect number
        givenNumberIsPerfect(28);

        // Test a non-perfect number
        givenNumberIsNotPerfect(10);

        // Test a negative number
        givenNegativeNumberIsNotPerfect(-6);
    }

    @Test
    void testIsPerfectEuclidEuler() {
        // Test a perfect number
        givenNumberIsPerfect(28);

        // Test a non-perfect number
        givenNumberIsNotPerfect(10);

        // Test a negative number
        givenNegativeNumberIsNotPerfect(-6);
    }

    private void givenNumberIsPerfect(int number) {
        assertTrue(PerfectNumber.isPerfectBruteForce(number));
    }

    private void givenNumberIsNotPerfect(int number) {
        assertFalse(PerfectNumber.isPerfectBruteForce(number));
    }

    private void givenNegativeNumberIsNotPerfect(int number) {
        assertFalse(PerfectNumber.isPerfectBruteForce(number));
    }
}