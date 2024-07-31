package com.baeldung.intrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntRangeValueRangeUnitTest {

    @Test
    void givenIntRangeValueRange_whenIsInClosedRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInClosedRange(10, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInClosedRange(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsNotInClosedRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInClosedRange(9, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInClosedRange(21, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsInOpenRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInOpenRange(11, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInOpenRange(19, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsNotInOpenRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInOpenRange(10, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsInOpenClosedRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInOpenClosedRange(11, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInOpenClosedRange(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsNotInOpenClosedRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInOpenClosedRange(10, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInOpenClosedRange(21, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsInClosedOpenRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInClosedOpenRange(10, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInClosedOpenRange(19, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeValueRange_whenIsNotInClosedOpenRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeValueRange.isInClosedOpenRange(9, 10, 20);
        boolean resultUpperBound = IntRangeValueRange.isInClosedOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }
}
