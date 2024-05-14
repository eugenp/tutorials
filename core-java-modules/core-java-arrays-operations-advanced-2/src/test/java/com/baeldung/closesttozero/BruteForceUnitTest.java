package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceUnitTest {

    @Test
    void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {1, 60, -10, 70, -80, 85};
        assertEquals(1, BruteForce.findClosestToZero(arr));

    }

    @Test
    void whenValidatingBruteForceComplexity_thenTimeShouldIncreaseLinearlyWithInputSize() throws IllegalAccessException {
        int[] arr1 = new Random().ints(1000, -1000000, 1000000)
                .toArray();
        long startTime1 = System.currentTimeMillis();
        BruteForce.findClosestToZero(arr1);
        long endTime1 = System.currentTimeMillis();
        long duration1 = endTime1 - startTime1;
        System.out.println("Time taken for array size 1000: " + duration1 + " milliseconds");

        int[] arr2 = new Random().ints(10000000, -1000000, 1000000)
                .toArray();
        long startTime2 = System.currentTimeMillis();
        BruteForce.findClosestToZero(arr2);
        long endTime2 = System.currentTimeMillis();
        long duration2 = endTime2 - startTime2;
        System.out.println("Time taken for array size 10000: " + duration2 + " milliseconds");

        int[] arr3 = new Random().ints(100000000, -1000000, 1000000)
                .toArray();
        long startTime3 = System.currentTimeMillis();
        BruteForce.findClosestToZero(arr3);
        long endTime3 = System.currentTimeMillis();
        long duration3 = endTime3 - startTime3;
        System.out.println("Time taken for array size 100000: " + duration3 + " milliseconds");

        assertTrue(duration2 > duration1);
        assertTrue(duration3 > duration2);
    }
}
