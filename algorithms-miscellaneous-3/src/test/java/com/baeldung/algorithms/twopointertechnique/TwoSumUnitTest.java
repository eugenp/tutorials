package com.baeldung.algorithms.twopointertechnique;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TwoSumUnitTest {

    private TwoSum twoSum = new TwoSum();

    private int[] sortedArray;

    private int targetValue;

    @Test
    public void givenASortedArrayOfIntegers_whenTwoSumSlow_thenPairExists() {

        sortedArray = new int[] { 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9 };

        targetValue = 12;

        assertTrue(twoSum.twoSumSlow(sortedArray, targetValue));
    }

    @Test
    public void givenASortedArrayOfIntegers_whenTwoSumSlow_thenPairDoesNotExists() {

        sortedArray = new int[] { 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9 };

        targetValue = 20;

        assertFalse(twoSum.twoSumSlow(sortedArray, targetValue));
    }

    @Test
    public void givenASortedArrayOfIntegers_whenTwoSum_thenPairExists() {

        sortedArray = new int[] { 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9 };

        targetValue = 12;

        assertTrue(twoSum.twoSum(sortedArray, targetValue));
    }

    @Test
    public void givenASortedArrayOfIntegers_whenTwoSum_thenPairDoesNotExists() {

        sortedArray = new int[] { 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9 };

        targetValue = 20;

        assertFalse(twoSum.twoSum(sortedArray, targetValue));
    }

}
