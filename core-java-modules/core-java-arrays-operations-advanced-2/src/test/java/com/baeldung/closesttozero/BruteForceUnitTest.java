package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BruteForceUnitTest {

    @Test
    void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {11, 60, -1, 70, -11, 85};
        assertEquals(-1, BruteForce.findClosestToZero(arr));
    }
}
