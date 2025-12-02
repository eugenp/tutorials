package com.baeldung.algorithms.sumoftwosquares;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class NumberAsSumOfTwoSquaresUnitTest {

    @Test
    @DisplayName("Given input number can be expressed as a sum of squares, when checked, then returns true")
    void givenNumberIsSumOfSquares_whenCheckIsCalled_thenReturnsTrue() {
        // Simple cases
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(0)); // 0^2 + 0^2
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(1)); // 1^2 + 0^2
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(5)); // 1^2 + 2^2
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(8)); // 2^2 + 2^2

        // Cases from Fermat theorem
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(50)); // 2 * 5^2. No 4k+3 primes.
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(45)); // 3^2 * 5. 4k+3 prime (3) has even exp.
        assertTrue(NumberAsSumOfTwoSquares.isSumOfTwoSquares(18)); // 2 * 3^2. 4k+3 prime (3) has even exp.
    }

    @Test
    @DisplayName("Given input number can't be expressed as a sum of squares, when checked, then returns false")
    void givenNumberIsNotSumOfSquares_whenCheckIsCalled_thenReturnsFalse() {
        // Simple cases
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(3));  // 3 (4k+3, exp 1)
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(6));  // 2 * 3 (3 has exp 1)
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(7));  // 7 (4k+3, exp 1)
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(11)); // 11 (4k+3, exp 1)

        // Cases from theorem
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(12)); // 2^2 * 3 (3 has exp 1)
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(21)); // 3 * 7 (both 3 and 7 have exp 1)
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(28)); // 2^2 * 7 (7 has exp 1)
    }

    @Test
    @DisplayName("Given input number is negative, when checked, then returns false")
    void givenNegativeNumber_whenCheckIsCalled_thenReturnsFalse() {
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(-1)); // Negatives as hygiene
        assertFalse(NumberAsSumOfTwoSquares.isSumOfTwoSquares(-10)); // Negatives as hygiene
    }
}
