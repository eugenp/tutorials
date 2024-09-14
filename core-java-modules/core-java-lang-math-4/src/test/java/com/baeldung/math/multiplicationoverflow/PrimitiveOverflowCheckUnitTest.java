package com.baeldung.math.multiplicationoverflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimitiveOverflowCheckUnitTest {

    @Test
    public void whenMultiplyingSmallIntValues_thenNoOverflowOccurs() {
        assertFalse(PrimitiveOverflowCheck.willOverflow(2, 3));
    }

    @Test
    public void whenMultiplyingTooLargeIntValues_thenOverflowOccurs() {
        assertTrue(PrimitiveOverflowCheck.willOverflow(Integer.MAX_VALUE, 3_000));
    }

    @Test
    public void whenMultiplyingSmallLongValues_thenNoOverflowOccurs() {
        assertFalse(PrimitiveOverflowCheck.willOverflow(1_000_000_000L, 10_000_000L));
    }

    @Test
    public void whenMultiplyingTooLargeLongValues_thenOverflowOccurs() {
        assertTrue(PrimitiveOverflowCheck.willOverflow(Long.MAX_VALUE, 2L));
    }
}
