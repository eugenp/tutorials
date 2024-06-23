package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueTOZeroUnitTest {
    @Test
    void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {1, 60, -10, 70, -80, 85};
        assertEquals(1, PriorityQueueToZero.findClosestToZeroWithPriorityQueue(arr, 1));
    }
}
