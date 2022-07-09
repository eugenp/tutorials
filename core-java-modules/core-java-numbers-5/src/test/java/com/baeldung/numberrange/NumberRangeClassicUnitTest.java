package com.baeldung.numberrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberRangeClassicUnitTest {

    // Test open range

    @Test
    void givenNumberRangeClassic_whenIsInOpenRange_thenSuccess() {
        // when - test open range
        boolean result = NumberRangeClassic.isInOpenRange(11, 10, 20);

        //then
        assertTrue(result);
    }

    @Test
    void givenNumberRangeClassic_whenIsNotInOpenRange_thenFailure() {
        // when - test closed range upper bound and lower bound
        boolean resultLowerBound = NumberRangeClassic.isInOpenRange(10, 10, 20);
        boolean resultUpperBound = NumberRangeClassic.isInOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    // Test closed range

    @Test
    void givenNumberRangeClassic_whenIsInClosedRange_thenSuccess() {
        // when - test closed range upper bound and lower bound
        boolean resultLowerBound = NumberRangeClassic.isInClosedRange(-10, -10, 5);
        boolean resultUpperBound = NumberRangeClassic.isInClosedRange(5, -10, 5);

        // then
        assertTrue(resultUpperBound);
        assertTrue(resultLowerBound);
    }

    @Test
    void givenNumberRangeClassic_whenIsNotInClosedRange_thenFailure() {
        // when - check out of range
        boolean resultLowerBound = NumberRangeClassic.isInClosedRange(-11, -10, 5);
        boolean resultUpperBound = NumberRangeClassic.isInClosedRange(6, -10, 5);

        //then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    // Test open closed range

    @Test
    void givenNumberRangeClassic_whenIsInOpenClosedRange_thenSuccess() {
        // when - check upper bound
        boolean result = NumberRangeClassic.isInOpenClosedRange(20, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenNumberRangeClassic_whenIsNotInOpenClosedRange_thenFailure() {
        // when - check lower bound
        boolean result = NumberRangeClassic.isInOpenClosedRange(10, 10, 20);

        // then
        assertFalse(result);
    }

    // Test open closed range

    @Test
    void givenNumberRangeClassic_whenIsInClosedOpenRange_thenSuccess() {
        // when - check lower bound
        boolean result = NumberRangeClassic.isInClosedOpenRange(10, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenNumberRangeClassic_whenIsNotInClosedOpenRange_thenFailure() {
        // when - check upper bound
        boolean result = NumberRangeClassic.isInClosedOpenRange(20, 10, 20);

        // then
        assertFalse(result);
    }
}
