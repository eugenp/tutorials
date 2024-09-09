package com.baeldung.math.multiplicationoverflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class OverflowCheckUnitTest {

    /* Rethrow the exception in the checkMultiplication() method before using this tests
    @Test
    public void whenMultiplyingLargeIntValues_thenArithmeticExceptionIsThrown() {
        assertThrows(ArithmeticException.class, () -> {
            OverflowCheck.checkMultiplication(Integer.MAX_VALUE, 2); // This should cause overflow
        });
    }

    @Test
    public void whenMultiplyingLargeLongValues_thenArithmeticExceptionIsThrown() {
        assertThrows(ArithmeticException.class, () -> {
            OverflowCheck.checkMultiplication(Long.MAX_VALUE, 2L); // This should cause overflow
        });
    }
     */

    @Test
    public void whenMultiplyingSmallLongValues_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> {
            OverflowCheck.checkMultiplication(1_000_000_000L, 10_000_000L); // This should not cause overflow
        });
    }

    @Test
    public void whenMultiplyingSmallIntValues_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> {
            OverflowCheck.checkMultiplication(1_000, 10_000); // This should not cause overflow
        });
    }
}
