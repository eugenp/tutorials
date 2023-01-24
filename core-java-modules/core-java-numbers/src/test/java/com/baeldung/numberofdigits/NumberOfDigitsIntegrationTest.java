package com.baeldung.numberofdigits;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class NumberOfDigitsIntegrationTest {
    
    private static NumberOfDigits numberOfDigits;
    
    static {
        numberOfDigits = new NumberOfDigits();
    }
    
    @DataPoints
    public static int[][] lowestIntegers()
    {
      return new int[][]{
          {1, 1}, 
          {2, 10}, 
          {3, 100}, 
          {4, 1000}, 
          {5, 10000}, 
          {6, 100000}, 
          {7, 1000000}, 
          {8, 10000000}, 
          {9, 100000000}, 
          {10, 1000000000}
      };
    }
    
    @DataPoints
    public static int[][] highestIntegers()
    {
      return new int[][]{
          {1, 9}, 
          {2, 99}, 
          {3, 999}, 
          {4, 9999}, 
          {5, 99999}, 
          {6, 999999}, 
          {7, 9999999}, 
          {8, 99999999}, 
          {9, 999999999}, 
          {10, Integer.MAX_VALUE}
      };
    }
    
    @DataPoints
    public static int[][] randomIntegers()
    {
      return new int[][]{
          {1, 1}, 
          {2, 14}, 
          {3, 549}, 
          {4, 1136}, 
          {5, 25340}, 
          {6, 134321}, 
          {7, 1435432}, 
          {8, 54234129}, 
          {9, 113683912}, 
          {10, 1534031982}
      };
    }

    @Theory
    public void givenDataPoints_whenStringBasedSolutionInvoked_thenAllPointsMatch(final int[] entry) {
        Assume.assumeTrue(entry[0] > 0 && entry[1] > 0);
        Assert.assertEquals(entry[0], numberOfDigits.stringBasedSolution(entry[1]));
    }

    @Theory
    public void givenDataPoints_whenLogarithmicApproachInvoked_thenAllPointsMatch(final int[] entry) {
        Assume.assumeTrue(entry[0] > 0 && entry[1] > 0);
        Assert.assertEquals(entry[0], numberOfDigits.logarithmicApproach(entry[1]));
    }

    @Theory
    public void givenDataPoints_whenRepeatedMultiplicationInvoked_thenAllPointsMatch(final int[] entry) {
        Assume.assumeTrue(entry[0] > 0 && entry[1] > 0);
        Assert.assertEquals(entry[0], numberOfDigits.repeatedMultiplication(entry[1]));
    }

    @Theory
    public void givenDataPoints_whenShiftOperatorsInvoked_thenAllPointsMatch(final int[] entry) {
        Assume.assumeTrue(entry[0] > 0 && entry[1] > 0);
        Assert.assertEquals(entry[0], numberOfDigits.shiftOperators(entry[1]));
    }

    @Theory
    public void givenDataPoints_whenDividingWithPowersOf2Invoked_thenAllPointsMatch(final int[] entry) {
        Assume.assumeTrue(entry[0] > 0 && entry[1] > 0);
        Assert.assertEquals(entry[0], numberOfDigits.dividingWithPowersOf2(entry[1]));
    }

    @Theory
    public void givenDataPoints_whenDivideAndConquerInvoked_thenAllPointsMatch(final int[] entry) {
        Assume.assumeTrue(entry[0] > 0 && entry[1] > 0);
        Assert.assertEquals(entry[0], numberOfDigits.divideAndConquer(entry[1]));
    }
    
}