package com.baeldung.fibonacci;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FibonacciSeriesUtilsUnitTest {

    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingRecursion() {
        int term = 10;
        int expectedValue = 55;
        assertEquals(FibonacciSeriesUtils.nthFibonacciTermRecursiveMethod(term), expectedValue);
    }

    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingIteration() {
        int term = 10;
        int expectedValue = 55;
        assertEquals(FibonacciSeriesUtils.nthFibonacciTermIterativeMethod(term), expectedValue);
    }

    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingBinetsFormula() {
        int term = 10;
        int expectedValue = 55;
        assertEquals(FibonacciSeriesUtils.nthFibonacciTermUsingBinetsFormula(term), expectedValue);
    }
}
