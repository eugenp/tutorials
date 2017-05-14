package com.baeldung.stackoverflowerror;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class InfiniteRecursionWithTerminationConditionManualTest {
    @Test
    public void givenPositiveIntNoOne_whenCalcFact_thenThrowsException() {
        int numToCalcFactorial = 1;
        InfiniteRecursionWithTerminationCondition irtc = new InfiniteRecursionWithTerminationCondition();

        assertEquals(1, irtc.calculateFactorial(numToCalcFactorial));
    }

    @Test
    public void givenPositiveIntGtOne_whenCalcFact_thenThrowsException() {
        int numToCalcFactorial = 5;
        InfiniteRecursionWithTerminationCondition irtc = new InfiniteRecursionWithTerminationCondition();

        assertEquals(120, irtc.calculateFactorial(numToCalcFactorial));
    }

    @Test(expected = StackOverflowError.class)
    public void givenNegativeInt_whenCalcFact_thenThrowsException() {
            int numToCalcFactorial = -1;
            InfiniteRecursionWithTerminationCondition irtc = new InfiniteRecursionWithTerminationCondition();

            irtc.calculateFactorial(numToCalcFactorial);
    }
}
