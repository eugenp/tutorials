package com.baeldung.numberrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberRangeApacheCommonsUnitTest {

    @Test
    void givenNumberRangeApacheCommons_whenIsInRange_thenSuccess() {
        // when - test closed range upper bound and lower bound
        boolean resultLowerBound = NumberRangeApacheCommons.isInRange(10, 10, 20);
        boolean resultUpperBound = NumberRangeApacheCommons.isInRange(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenNumberRangeApacheCommons_whenIsInRange_thenFailure() {
        // when - test out of range upper bound and lower bound
        boolean resultLowerBound = NumberRangeApacheCommons.isInRange(8, 10, 20);
        boolean resultUpperBound = NumberRangeApacheCommons.isInRange(22, 10, 20);

        // then
        assertFalse(resultLowerBound);
        assertFalse(resultUpperBound);
    }
}
