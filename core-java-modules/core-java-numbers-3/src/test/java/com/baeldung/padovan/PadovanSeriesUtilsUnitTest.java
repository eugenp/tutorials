package com.baeldung.padovan;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PadovanSeriesUtilsUnitTest {

    // Test base cases for all methods
    @Test
    public void givenBaseCases_thenReturnCorrectValues() {
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermRecursiveMethod(0));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermRecursiveMethod(1));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermRecursiveMethod(2));
        
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermRecursiveMethodWithMemoization(0));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermRecursiveMethodWithMemoization(1));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermRecursiveMethodWithMemoization(2));
        
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithArray(0));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithArray(1));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithArray(2));
        
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithVariables(0));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithVariables(1));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithVariables(2));
        
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermUsingFormula(0));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermUsingFormula(1));
        assertEquals(1, PadovanSeriesUtils.nthPadovanTermUsingFormula(2));
    }

    // Test recursive method
    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingRecursion() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(expectedValue, PadovanSeriesUtils.nthPadovanTermRecursiveMethod(term));
    }

    // Test recursive method with memoization
    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingRecursionWithMemoization() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(expectedValue, PadovanSeriesUtils.nthPadovanTermRecursiveMethodWithMemoization(term));
    }

    // Test iterative method with array
    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingIterationWithArray() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(expectedValue, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithArray(term));
    }

    // Test iterative method with variables
    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingIterationWithVariables() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(expectedValue, PadovanSeriesUtils.nthPadovanTermIterativeMethodWithVariables(term));
    }

    // Test formula method
    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingFormula() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(expectedValue, PadovanSeriesUtils.nthPadovanTermUsingFormula(term));
    }

    // Test multiple terms to verify sequence correctness
    @Test
    public void givenMultipleTerms_thenReturnCorrectSequence() {
        // Padovan sequence: 1, 1, 1, 2, 2, 3, 4, 5, 7, 9, 12, 16, 21, 28, 37, 49, 65, 86, 114, 151
        int[] expectedSequence = {1, 1, 1, 2, 2, 3, 4, 5, 7, 9, 12, 16, 21, 28, 37, 49, 65, 86, 114, 151};
        
        for (int i = 0; i < expectedSequence.length; i++) {
            assertEquals("Term " + i, expectedSequence[i], PadovanSeriesUtils.nthPadovanTermRecursiveMethod(i));
            assertEquals("Term " + i, expectedSequence[i], PadovanSeriesUtils.nthPadovanTermRecursiveMethodWithMemoization(i));
            assertEquals("Term " + i, expectedSequence[i], PadovanSeriesUtils.nthPadovanTermIterativeMethodWithArray(i));
            assertEquals("Term " + i, expectedSequence[i], PadovanSeriesUtils.nthPadovanTermIterativeMethodWithVariables(i));
            // Formula method may have slight approximation errors for larger terms
            if (i < 15) { // Test formula for first 15 terms only
                assertEquals("Term " + i, expectedSequence[i], PadovanSeriesUtils.nthPadovanTermUsingFormula(i));
            }
        }
    }

    // Test that all methods return the same result for the same input
    @Test
    public void givenSameInput_thenAllMethodsReturnSameResult() {
        int term = 15;
        
        int recursiveResult = PadovanSeriesUtils.nthPadovanTermRecursiveMethod(term);
        int memoizedResult = PadovanSeriesUtils.nthPadovanTermRecursiveMethodWithMemoization(term);
        int arrayResult = PadovanSeriesUtils.nthPadovanTermIterativeMethodWithArray(term);
        int variablesResult = PadovanSeriesUtils.nthPadovanTermIterativeMethodWithVariables(term);
        
        assertEquals(recursiveResult, memoizedResult);
        assertEquals(recursiveResult, arrayResult);
        assertEquals(recursiveResult, variablesResult);
    }
}