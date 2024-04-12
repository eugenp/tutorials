package com.baeldung.exceptions.stackoverflowerror;


import org.junit.Test;

public class UnintendedInfiniteRecursionManualTest {
    @Test(expected = StackOverflowError.class)
    public void givenPositiveIntNoOne_whenCalFact_thenThrowsException() {
        int numToCalcFactorial = 1;
        UnintendedInfiniteRecursion uir = new UnintendedInfiniteRecursion();

        uir.calculateFactorial(numToCalcFactorial);
    }

    @Test(expected = StackOverflowError.class)
    public void givenPositiveIntGtOne_whenCalcFact_thenThrowsException() {
        int numToCalcFactorial = 2;
        UnintendedInfiniteRecursion uir = new UnintendedInfiniteRecursion();

        uir.calculateFactorial(numToCalcFactorial);
    }

    @Test(expected = StackOverflowError.class)
    public void givenNegativeInt_whenCalcFact_thenThrowsException() {
        int numToCalcFactorial = -1;
        UnintendedInfiniteRecursion uir = new UnintendedInfiniteRecursion();

        uir.calculateFactorial(numToCalcFactorial);
    }
}
