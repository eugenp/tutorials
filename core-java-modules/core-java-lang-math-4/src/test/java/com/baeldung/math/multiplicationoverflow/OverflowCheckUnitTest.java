package com.baeldung.math.multiplicationoverflow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OverflowCheckUnitTest {

    @Test
    public void whenMultiplyingSmallIntValues_thenResultIsReturned() {
        // Check if result message is returned for valid int multiplication
        String result = OverflowCheck.checkMultiplication(5_000, 3_000);
        Assertions.assertEquals("Result (int): 15000000", result);
    }

    @Test
    public void whenMultiplyingLargeIntValues_thenOverflowMessageIsReturned() {
        // Check if overflow message is returned for int multiplication
        String result = OverflowCheck.checkMultiplication(Integer.MAX_VALUE, 2);
        Assertions.assertEquals("Overflow occurred for int!", result);
    }

    @Test
    public void whenMultiplyingLargeLongValues_thenResultIsReturned() {
        // Check if result message is returned for valid long multiplication
        String result = OverflowCheck.checkMultiplication(1_000_000_000L, 10_000_000L);
        Assertions.assertEquals("Result (long): 10000000000000000", result);
    }

    @Test
    public void whenMultiplyingTooLargeLongValues_thenOverflowMessageIsReturned() {
        // Check if overflow message is returned for long multiplication
        String result = OverflowCheck.checkMultiplication(Long.MAX_VALUE, 2);
        Assertions.assertEquals("Overflow occurred for long!", result);
    }
}