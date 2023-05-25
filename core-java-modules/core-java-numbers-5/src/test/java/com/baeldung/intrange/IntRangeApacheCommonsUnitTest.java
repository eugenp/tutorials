package com.baeldung.intrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntRangeApacheCommonsUnitTest {

    @Test
    void givenIntRangeApacheCommons_whenIsInClosedRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInClosedRange(10, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInClosedRange(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsNotInClosedRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInClosedRange(8, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInClosedRange(22, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsInOpenRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInOpenRange(11, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInOpenRange(19, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsNotInOpenRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInOpenRange(10, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsInOpenClosedRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInOpenClosedRange(11, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInOpenClosedRange(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsNotInOpenClosedRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInOpenClosedRange(10, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInOpenClosedRange(21, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsInClosedOpenRange_thenSuccess() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInClosedOpenRange(10, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInClosedOpenRange(19, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenIntRangeApacheCommons_whenIsNotInClosedOpenRange_thenFailure() {
        // when
        boolean resultLowerBound = IntRangeApacheCommons.isInClosedOpenRange(9, 10, 20);
        boolean resultUpperBound = IntRangeApacheCommons.isInClosedOpenRange(20, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }
}
