package com.baeldung.math.multiplicationoverflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrimitiveOverflowCheckUnitTest {

    @Test
    public void whenMultiplyingLargeIntValues_thenOverflowIsDetected() {
        assertTrue(PrimitiveOverflowCheck.willOverflow(Integer.MAX_VALUE, 2)); // 2 * Integer.MAX_VALUE is too large
    }

    @Test
    public void whenMultiplyingNegativeIntValues_thenOverflowIsDetected() {
        assertTrue(PrimitiveOverflowCheck.willOverflow(Integer.MIN_VALUE, -1)); // -1 * Integer.MIN_VALUE is too large
    }

    @Test
    public void whenMultiplyingZeroIntValues_thenNoOverflowIsDetected() {
        assertFalse(PrimitiveOverflowCheck.willOverflow(0, 0)); // 0 * 0 is within int range
    }

    @Test
    public void whenMultiplyingLargeLongValues_thenOverflowIsDetected() {
        assertTrue(PrimitiveOverflowCheck.willOverflow(Long.MAX_VALUE, 2L)); // 2 * Long.MAX_VALUE is too large
    }

    @Test
    public void whenMultiplyingNegativeLongValues_thenOverflowIsDetected() {
        assertTrue(PrimitiveOverflowCheck.willOverflow(Long.MIN_VALUE, -1L)); // -1 * Long.MIN_VALUE is too large
    }

    @Test
    public void whenMultiplyingSmallLongValues_thenNoOverflowIsDetected() {
        assertFalse(PrimitiveOverflowCheck.willOverflow(1_000_000_000L, 10_000_000L)); // 1_000_000_000 * 10_000_000 is within long range
    }

    @Test
    public void whenMultiplyingZeroLongValues_thenNoOverflowIsDetected() {
        assertFalse(PrimitiveOverflowCheck.willOverflow(0L, 0L)); // 0 * 0 is within long range
    }
}
