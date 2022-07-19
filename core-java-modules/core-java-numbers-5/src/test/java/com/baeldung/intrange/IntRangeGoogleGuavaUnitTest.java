package com.baeldung.intrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntRangeGoogleGuavaUnitTest {

    @Test
    void givenIntRangeGoogleGuava_whenIsInOpenRange_thenSuccess() {
        // when
        boolean result = IntRangeGoogleGuava.isInOpenRange(14, 10, 20);

        //then
        assertTrue(result);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsNotInOpenRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeGoogleGuava.isInOpenRange(10, 10, 20);
        boolean resultUpperBound = IntRangeGoogleGuava.isInOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsInClosedRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeGoogleGuava.isInClosedRange(-10, -10, 5);
        boolean resultUpperBound = IntRangeGoogleGuava.isInClosedRange(5, -10, 5);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsNotInClosedRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeGoogleGuava.isInClosedRange(-11, -10, 5);
        boolean resultUpperBound = IntRangeGoogleGuava.isInClosedRange(6, -10, 5);

        //then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsInOpenClosedRange_thenSuccess() {
        // when
        boolean result = IntRangeGoogleGuava.isInOpenClosedRange(20, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsNotInOpenClosedRange_thenFailure() {
        // when
        boolean result = IntRangeGoogleGuava.isInOpenClosedRange(10, 10, 20);

        // then
        assertFalse(result);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsInClosedOpenRange_thenSuccess() {
        // when
        boolean result = IntRangeGoogleGuava.isInClosedOpenRange(10, 10, 20);

        // then
        assertTrue(result);
    }

    @Test
    void givenIntRangeGoogleGuava_whenIsNotInClosedOpenRange_thenFailure() {
        // when
        boolean result = IntRangeGoogleGuava.isInClosedOpenRange(20, 10, 20);

        // then
        assertFalse(result);
    }
}
