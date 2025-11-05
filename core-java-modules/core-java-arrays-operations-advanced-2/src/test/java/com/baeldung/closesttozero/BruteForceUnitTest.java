package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BruteForceUnitTest {

    @Test
    void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {10,2,4,-2,12,25};
        assertEquals(2, BruteForce.findClosestToZero(arr));
    }
}
