package com.baeldung.algorithms.checktargetsum;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckTargetSumUnitTest {
    private CheckTargetSum checkTargetSum = new CheckTargetSum();

    private int[] nums = new int[] { 10, 5, 15, 7, 14, 1, 9 };

    private int existingTarget = 6;

    private int nonExistingTarget = 27;

    @Test
    public void givenArrayOfIntegers_whenTargetSumNaive_thenPairExists() {
        assertTrue(checkTargetSum.isTargetSumExistNaive(nums, existingTarget));
    }

    @Test
    public void givenArrayOfIntegers_whenTargetSumNaive_thenPairDoesNotExists() {
        assertFalse(checkTargetSum.isTargetSumExistNaive(nums, nonExistingTarget));
    }

    @Test
    public void givenArrayOfIntegers_whenTargetSumSorted_thenPairExists() {
        assertTrue(checkTargetSum.isTargetSumExistNaive(nums, existingTarget));
    }

    @Test
    public void givenArrayOfIntegers_whenTargetSumSorted_thenPairDoesNotExists() {
        assertFalse(checkTargetSum.isTargetSumExistNaive(nums, nonExistingTarget));
    }

    @Test
    public void givenArrayOfIntegers_whenTargetSumHashSet_thenPairExists() {
        assertTrue(checkTargetSum.isTargetSumExistNaive(nums, existingTarget));
    }

    @Test
    public void givenArrayOfIntegers_whenTargetSumHashSet_thenPairDoesNotExists() {
        assertFalse(checkTargetSum.isTargetSumExistNaive(nums, nonExistingTarget));
    }
}
