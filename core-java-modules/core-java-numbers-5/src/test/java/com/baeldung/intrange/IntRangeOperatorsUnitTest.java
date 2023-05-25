package com.baeldung.intrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntRangeOperatorsUnitTest {

    @Test
    void givenIntRangeOperators_whenIsInOpenRange_thenSuccess() {
        // when
        boolean result = IntRangeOperators.isInOpenRange(11, 10, 20);

        //then
        assertTrue(result);
    }

    @Test
    void givenIntRangeOperators_whenIsNotInOpenRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeOperators.isInOpenRange(10, 10, 20);
        boolean resultUpperBound = IntRangeOperators.isInOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeOperators_whenIsInClosedRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeOperators.isInClosedRange(-10, -10, 5);
        boolean resultUpperBound = IntRangeOperators.isInClosedRange(5, -10, 5);

        // then
        assertTrue(resultUpperBound);
        assertTrue(resultLowerBound);
    }

    @Test
    void givenIntRangeOperators_whenIsNotInClosedRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeOperators.isInClosedRange(-11, -10, 5);
        boolean resultUpperBound = IntRangeOperators.isInClosedRange(6, -10, 5);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeOperators_whenIsInOpenClosedRange_thenSuccess() {
        // when
        boolean result = IntRangeOperators.isInOpenClosedRange(20, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenIntRangeOperators_whenIsNotInOpenClosedRange_thenFailure() {
        // when
        boolean result = IntRangeOperators.isInOpenClosedRange(10, 10, 20);

        // then
        assertFalse(result);
    }

    @Test
    void givenIntRangeOperators_whenIsInClosedOpenRange_thenSuccess() {
        // when
        boolean result = IntRangeOperators.isInClosedOpenRange(10, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenIntRangeOperators_whenIsNotInClosedOpenRange_thenFailure() {
        // when
        boolean result = IntRangeOperators.isInClosedOpenRange(20, 10, 20);

        // then
        assertFalse(result);
    }
}
