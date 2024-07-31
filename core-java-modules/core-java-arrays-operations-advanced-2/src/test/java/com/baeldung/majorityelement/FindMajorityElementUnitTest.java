package com.baeldung.majorityelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FindMajorityElementUnitTest {

    @Test
    void givenArrayWithMajorityElement_WhenUsingForLoop_ThenReturnFound() {
        int[] nums = { 2, 3, 2, 4, 2, 5, 2 };
        Integer result = FindMajorityElement.findMajorityElementUsingForLoop(nums);
        assertEquals(2, result);
    }

    @Test
    void givenArrayWithoutMajorityElement_WhenUsingForLoop_ThenNotFound() {
        int[] nums = { 2, 2, 3, 3 };
        Integer result = FindMajorityElement.findMajorityElementUsingForLoop(nums);
        assertEquals(null, result);
    }

    @Test
    public void givenArrayWithMajorityElement_WhenUsingSorting_ThenReturnFound() {
        int[] nums = { 2, 3, 2, 4, 2, 5, 2 };
        Integer result = FindMajorityElement.findMajorityElementUsingSorting(nums);
        assertEquals(2, result);
    }

    @Test
    public void givenArrayWithoutMajorityElement_WhenUsingSorting_ThenNotFound() {
        int[] nums = { 2, 2, 3, 3 };
        Integer result = FindMajorityElement.findMajorityElementUsingSorting(nums);
        assertEquals(null, result);
    }

    @Test
    void givenArrayWithMajorityElement_WhenUsingHashMap_ThenReturnFound() {
        int[] nums = { 2, 3, 2, 4, 2, 5, 2 };
        Integer result = FindMajorityElement.findMajorityElementUsingHashMap(nums);
        assertEquals(2, result);
    }

    @Test
    void givenArrayWithoutMajorityElement_WhenUsingHashMap_ThenNotFound() {
        int[] nums = { 2, 2, 3, 3 };
        Integer result = FindMajorityElement.findMajorityElementUsingHashMap(nums);
        assertEquals(null, result);
    }

    @Test
    void givenArrayWithMajorityElement_WhenUsingMooreVoting_ThenReturnFound() {
        int[] nums = { 2, 3, 2, 4, 2, 5, 2 };
        Integer result = FindMajorityElement.findMajorityElementUsingMooreVoting(nums);
        assertEquals(2, result);
    }

    @Test
    void givenArrayWithoutMajorityElement_WhenUsingMooreVoting_ThenNotFound() {
        int[] nums = { 2, 2, 3, 3 };
        Integer result = FindMajorityElement.findMajorityElementUsingMooreVoting(nums);
        assertEquals(null, result);
    }
}
