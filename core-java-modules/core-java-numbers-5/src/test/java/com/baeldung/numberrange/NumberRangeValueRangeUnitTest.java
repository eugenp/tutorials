package com.baeldung.numberrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberRangeValueRangeUnitTest {

    @Test
    void givenNumberRangeValueRange_whenIsInRange_thenSuccess() {
        // when - check upper and lower bounds
        boolean resultLowerBound = NumberRangeValueRange.isInRange(10, 10, 20);
        boolean resultUpperBound = NumberRangeValueRange.isInRange(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenNumberRangeValueRange_whenIsNotInRange_thenFailure() {
        // when
        boolean result = NumberRangeValueRange.isInRange(9, 10, 20);

        // then
        assertFalse(result);
    }
}
