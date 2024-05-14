package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SortingAndBinarySearchUnitTest {

    @Test
    void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {0, 60, -10, 70, -80, 85};
        assertEquals(0, SortingAndBinarySearch.findClosestToZero(arr));
    }

    @Test
    public void whenFindingClosestToZeroWithSortingAndBinarySearch_thenShouldNotExceedTimeLimit() {
        int[] arr = new Random().ints(1000000, -1000000, 1000000)
                .toArray();

        assertTimeout(Duration.ofSeconds(1), () -> {
            SortingAndBinarySearch.findClosestToZero(arr);
        });
    }

    @Test
    void whenValidatingOptimizedApproachTimeComplexity_thenTimeShouldRemainConstantWithInputSize() {
        int[] arr1 = new Random().ints(1000, -1000000, 1000000).toArray();
        long startTime1 = System.currentTimeMillis();
        SortingAndBinarySearch.findClosestToZero(arr1);
        long endTime1 = System.currentTimeMillis();
        long duration1 = endTime1 - startTime1;

        int[] arr2 = new Random().ints(10000000, -1000000, 1000000).toArray();
        long startTime2 = System.currentTimeMillis();
        SortingAndBinarySearch.findClosestToZero(arr2);
        long endTime2 = System.currentTimeMillis();
        long duration2 = endTime2 - startTime2;

        int[] arr3 = new Random().ints(100000000, -1000000, 1000000).toArray();
        long startTime3 = System.currentTimeMillis();
        SortingAndBinarySearch.findClosestToZero(arr3);
        long endTime3 = System.currentTimeMillis();
        long duration3 = endTime3 - startTime3;

        assertTrue( duration1 <= 1);
        assertTrue( duration2 <= 1);
        assertTrue(duration3  <= 1);
    }
}
