package com.baeldung.exceptions.stackoverflowerror;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

public class RecursionWithCorrectTerminationConditionManualTest {
    @Test
    public void givenNegativeInt_whenCalcFact_thenCorrectlyCalc() {
        int numToCalcFactorial = -1;
        RecursionWithCorrectTerminationCondition rctc = new RecursionWithCorrectTerminationCondition();

        assertEquals(1, rctc.calculateFactorial(numToCalcFactorial));
    }
}
