package com.baeldung.algorithms.perfectnumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerfectNumberUnitTest {
    @Test
    void givenPerfectNumber_whenCheckingIsPerfectBruteForce_thenReturnTrue() {
        assertTrue(PerfectNumber.isPerfectBruteForce(6));
    }

    @Test
    void givenNonPerfectNumber_whenCheckingIsPerfectBruteForce_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectBruteForce(10));
    }

    @Test
    void givenNegativeNumber_whenCheckingIsPerfectBruteForce_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectBruteForce(-28));
    }

    @Test
    void givenPerfectNumber_whenCheckingIsPerfectStream_thenReturnTrue() {
        assertTrue(PerfectNumber.isPerfectStream(28));
    }

    @Test
    void givenNonPerfectNumber_whenCheckingIsPerfectStream_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectStream(10));
    }

    @Test
    void givenNegativeNumber_whenCheckingIsPerfectStream_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectStream(-6));
    }

    @Test
    void givenPerfectNumber_whenCheckingIsPerfectEuclidEuler_thenReturnTrue() {
        assertTrue(PerfectNumber.isPerfectEuclidEuler(28));
    }

    @Test
    void givenNonPerfectNumber_whenCheckingIsPerfectEuclidEuler_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectEuclidEuler(10));
    }

    @Test
    void givenNegativeNumber_whenCheckingIsPerfectEuclidEuler_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectEuclidEuler(-6));
    }

    @Test
    void givenPerfectNumber_whenCheckingIsPerfectEuclidEulerUsingShift_thenReturnTrue() {
        assertTrue(PerfectNumber.isPerfectEuclidEulerUsingShift(28));
    }

    @Test
    void givenNonPerfectNumber_whenCheckingIsPerfectEuclidEulerUsingShift_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectEuclidEulerUsingShift(10));
    }

    @Test
    void givenNegativeNumber_whenCheckingIsPerfectEuclidEulerUsingShift_thenReturnFalse() {
        assertFalse(PerfectNumber.isPerfectEuclidEulerUsingShift(-6));
    }
}