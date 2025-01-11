package com.baeldung.math.multiplicationoverflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimitiveOverflowCheckUnitTest {
@Test
public void givenVariousInputs_whenCheckingForOverflow_thenOverflowIsDetectedCorrectly() {
    // Int tests
    assertFalse(PrimitiveOverflowCheck.willOverflow(2, 3)); // No overflow
    assertTrue(PrimitiveOverflowCheck.willOverflow(Integer.MAX_VALUE, 3_000)); // Overflow
    assertFalse(PrimitiveOverflowCheck.willOverflow(100, -200)); // No overflow (positive * negative)
    assertTrue(PrimitiveOverflowCheck.willOverflow(Integer.MIN_VALUE, -2)); // Overflow (negative * negative)
    assertFalse(PrimitiveOverflowCheck.willOverflow(-100, -200)); // No overflow (small negative values)
    assertFalse(PrimitiveOverflowCheck.willOverflow(0, 1000)); // No overflow (multiplying with zero)

    // Long tests
    assertFalse(PrimitiveOverflowCheck.willOverflow(1_000_000_000L, 10_000_000L)); // No overflow
    assertTrue(PrimitiveOverflowCheck.willOverflow(Long.MAX_VALUE, 2L)); // Overflow
    assertFalse(PrimitiveOverflowCheck.willOverflow(1_000_000_000L, -10_000L)); // No overflow (positive * negative)
    assertTrue(PrimitiveOverflowCheck.willOverflow(Long.MIN_VALUE, -2L)); // Overflow (negative * negative)
    assertFalse(PrimitiveOverflowCheck.willOverflow(-1_000_000L, -10_000L)); // No overflow (small negative values)
    assertFalse(PrimitiveOverflowCheck.willOverflow(0L, 1000L)); // No overflow (multiplying with zero)
}
}
