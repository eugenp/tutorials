package com.baeldung.numberrange;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberRangeFormulaUnitTest {

    @Test
    void givenNumberRangeFormula_whenIsInRange_thenSuccess() {
        // when - check upper and lower bound
        boolean resultLowerBound = NumberRangeFormula.isInRangeUsingFormula(10, 10, 20);
        boolean resultUpperBound = NumberRangeFormula.isInRangeUsingFormula(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenNumberRangeFormula_whenIsNotInRange_thenFailure() {
        // when - test out of range
        boolean result = NumberRangeFormula.isInRangeUsingFormula(9, 10, 20);

        // then
        assertFalse(result);
    }

    @Test
    void givenNumberRangeFormula2_whenIsInRange_thenSuccess() {
        // when - check upper and lower bound
        boolean resultLowerBound = NumberRangeFormula.isInRangeUsingFormula2(10, 10, 20);
        boolean resultUpperBound = NumberRangeFormula.isInRangeUsingFormula2(20, 10, 20);

        // then
        assertTrue(resultLowerBound);
        assertTrue(resultUpperBound);
    }

    @Test
    void givenNumberRangeFormula2_whenIsNotInRange_thenFailure() {
        // when - test out of range
        boolean result = NumberRangeFormula.isInRangeUsingFormula2(9, 10, 20);

        // then
        assertFalse(result);
    }
}
