package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueTOZeroUnitTest {
    @Test
    public void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {1, 60, -10, 70, -80, 85};
        assertEquals(1, PriorityQueueToZero.findClosestToZeroWithPriorityQueue(arr, 1));
    }

    @Test
    void whenValidatingPriorityQueueTimeComplexity_thenTimeShouldIncreaseWithInputSize() {
        int[] arr1 = new Random().ints(1000, -1000000, 1000000)
                .toArray();
        long startTime1 = System.currentTimeMillis();
        PriorityQueueToZero.findClosestToZeroWithPriorityQueue(arr1, 1);
        long endTime1 = System.currentTimeMillis();
        long duration1 = endTime1 - startTime1;

        int[] arr2 = new Random().ints(10000, -1000000, 1000000)
                .toArray();
        long startTime2 = System.currentTimeMillis();
        PriorityQueueToZero.findClosestToZeroWithPriorityQueue(arr2, 1);
        long endTime2 = System.currentTimeMillis();
        long duration2 = endTime2 - startTime2;

        int[] arr3 = new Random().ints(100000, -1000000, 1000000)
                .toArray();
        long startTime3 = System.currentTimeMillis();
        PriorityQueueToZero.findClosestToZeroWithPriorityQueue(arr3, 1);
        long endTime3 = System.currentTimeMillis();
        long duration3 = endTime3 - startTime3;

        assertTrue(duration3 > duration1);
    }

}
