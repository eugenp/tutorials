package com.baeldung.math.multiplicationoverflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OverflowCheckUnitTest {

@Test
public void givenVariousInputs_whenCheckingForOverflow_thenOverflowIsDetectedCorrectly() {
    // Int tests
    assertTrue(OverflowCheck.checkMultiplication(2, 3)); // No overflow
    assertFalse(OverflowCheck.checkMultiplication(Integer.MAX_VALUE, 3_000)); // Overflow
    assertTrue(OverflowCheck.checkMultiplication(100, -200)); // No overflow (positive * negative)
    assertFalse(OverflowCheck.checkMultiplication(Integer.MIN_VALUE, -2)); // Overflow (negative * negative)
    assertTrue(OverflowCheck.checkMultiplication(-100, -200)); // No overflow (small negative values)
    assertTrue(OverflowCheck.checkMultiplication(0, 1000)); // No overflow (multiplying with zero)

    // Long tests
    assertTrue(OverflowCheck.checkMultiplication(1_000_000_000L, 10_000_000L)); // No overflow
    assertFalse(OverflowCheck.checkMultiplication(Long.MAX_VALUE, 2L)); // Overflow
    assertTrue(OverflowCheck.checkMultiplication(1_000_000_000L, -10_000L)); // No overflow (positive * negative)
    assertFalse(OverflowCheck.checkMultiplication(Long.MIN_VALUE, -2L)); // Overflow (negative * negative)
    assertTrue(OverflowCheck.checkMultiplication(-1_000_000L, -10_000L)); // No overflow (small negative values)
    assertTrue(OverflowCheck.checkMultiplication(0L, 1000L)); // No overflow (multiplying with zero)
}
}
