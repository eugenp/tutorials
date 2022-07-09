package com.baeldung.numberrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberRangeGoogleGuavaUnitTest {

    // Test open range

    @Test
    void givenNumberGoogleGuava_whenIsInOpenRange_thenSuccess() {
        // when
        boolean result = NumberRangeGoogleGuava.isInOpenRange(14, 10, 20);

        //then
        assertTrue(result);
    }

    @Test
    void givenNumberGoogleGuava_whenIsNotInOpenRange_thenFailure() {
        // when - check both lower and upper bound
        boolean resultLowerBound = NumberRangeGoogleGuava.isInOpenRange(10, 10, 20);
        boolean resultUpperBound = NumberRangeGoogleGuava.isInOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    // Test closed range

    @Test
    void givenNumberGoogleGuava_whenIsInClosedRange_thenSuccess() {
        // when - test closed range upper bound and lower bound
        boolean resultLowerBound = NumberRangeGoogleGuava.isInClosedRange(-10, -10, 5);
        boolean resultUpperBound = NumberRangeGoogleGuava.isInClosedRange(5, -10, 5);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenNumberGoogleGuava_whenIsNotInClosedRange_thenFailure() {
        // when - check out of range
        boolean resultLowerBound = NumberRangeGoogleGuava.isInClosedRange(-11, -10, 5);
        boolean resultUpperBound = NumberRangeGoogleGuava.isInClosedRange(6, -10, 5);

        //then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    // Test open closed range

    @Test
    void givenNumberGoogleGuava_whenIsInOpenClosedRange_thenSuccess() {
        // when - check upper bound
        boolean result = NumberRangeGoogleGuava.isInOpenClosedRange(20, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenNumberGoogleGuava_whenIsNotInOpenClosedRange_thenFailure() {
        // when - check lower bound
        boolean result = NumberRangeGoogleGuava.isInOpenClosedRange(10, 10, 20);

        // then
        assertFalse(result);
    }

    // Test open closed range

    @Test
    void givenNumberGoogleGuava_whenIsInClosedOpenRange_thenSuccess() {
        // when - check lower bound
        boolean result = NumberRangeGoogleGuava.isInClosedOpenRange(10, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenNumberGoogleGuava_whenIsNotInClosedOpenRange_thenFailure() {
        // when - check upper bound
        boolean result = NumberRangeGoogleGuava.isInClosedOpenRange(20, 10, 20);

        // then
        assertFalse(result);
    }
}
