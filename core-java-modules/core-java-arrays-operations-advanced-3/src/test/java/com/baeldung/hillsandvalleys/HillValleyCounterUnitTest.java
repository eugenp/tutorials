package com.baeldung.hillsandvalleys;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HillValleyCounterUnitTest {

    HillValleyCounter hillValleyCounter;

    @Before
    public void setup() {
        hillValleyCounter = new HillValleyCounter();
    }

    @Test
    public void givenArrayOfIntegers_whenCountHillsAndValleys_thenReturnCorrectCount() {
        int[] numbers = { 4, 5, 6, 5, 4, 5, 4 };
        int expected = 3;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void givenArrayOfStrictlyIncreasingIntegers_whenCountHillsAndValleys_thenReturnCountZero() {
        int[] numbers = { 1, 2, 3, 4, 5, 6 };
        int expected = 0;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void givenArrayOfConstantIntegers_whenCountHillsAndValleys_thenReturnCountZero() {
        int[] numbers = { 5, 5, 5, 5, 5 };
        int expected = 0;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void givenArrayWithNoHillOrValley_whenCountHillsAndValleys_thenReturnCountZero() {
        int[] numbers = { 6, 6, 5, 5, 4, 1 };
        int expected = 0;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void givenArrayWithFlattenValley_whenCountsHillAndValleys_thenReturnCountOne() {
        int[] numbers = { 6, 5, 4, 4, 4, 5, 6 };
        int expected = 1;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void givenArrayWithFlattenHill_whenCountHillsAndValleys_thenReturnCountOne() {
        int[] numbers = { 1, 2, 4, 4, 4, 2, 1 };
        int expected = 1;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void givenArrayWithSingleElement_whenCountHillsAndValleys_thenReturnCountZero() {
        int[] numbers = { 1 };
        int expected = 0;
        int result = hillValleyCounter.countHillsAndValleys(numbers);
        assertEquals(expected, result);
    }
}
