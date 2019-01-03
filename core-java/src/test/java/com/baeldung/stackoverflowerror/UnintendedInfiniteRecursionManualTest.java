package com.baeldung.stackoverflowerror;


import org.junit.Test;

/**
 * 测试：非预期无限递归
 */
public class UnintendedInfiniteRecursionManualTest {

    @Test(expected = StackOverflowError.class)
    public void givenPositiveIntNoOne_whenCalFact_thenThrowsException() {
        try {
            int numToCalcFactorial = 1;
            UnintendedInfiniteRecursion uir = new UnintendedInfiniteRecursion();

            uir.calculateFactorial(numToCalcFactorial);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Test(expected = StackOverflowError.class)
    public void givenPositiveIntGtOne_whenCalcFact_thenThrowsException() {
        try{
            int numToCalcFactorial = 2;
            UnintendedInfiniteRecursion uir = new UnintendedInfiniteRecursion();

            uir.calculateFactorial(numToCalcFactorial);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Test(expected = StackOverflowError.class)
    public void givenNegativeInt_whenCalcFact_thenThrowsException() {
        try{
            int numToCalcFactorial = -1;
            UnintendedInfiniteRecursion uir = new UnintendedInfiniteRecursion();

            uir.calculateFactorial(numToCalcFactorial);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }
}
