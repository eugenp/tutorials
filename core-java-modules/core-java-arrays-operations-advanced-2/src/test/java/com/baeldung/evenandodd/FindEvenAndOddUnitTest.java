package com.baeldung.evenandodd;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindEvenAndOddUnitTest {
    private static final int[] NUMBERS = new int[]{1, 2, 3, 4, 5, 6};

    @Test
    public void testFindEvenNumbersWithLoop() {
        List<Integer> expectedEvenNumbers = Arrays.asList(2, 4, 6);
        List<Integer> actualEvenNumbers = FindEvenAndOdd.findEvenNumbersWithLoop(NUMBERS);
        assertEquals(expectedEvenNumbers, actualEvenNumbers, "The even numbers should be [2, 4, 6]");
    }

    @Test
    public void testFindOddNumbersWithLoop() {
        List<Integer> expectedOddNumbers = Arrays.asList(1, 3, 5);
        List<Integer> actualOddNumbers = FindEvenAndOdd.findOddNumbersWithLoop(NUMBERS);
        assertEquals(expectedOddNumbers, actualOddNumbers, "The odd numbers should be [1, 3, 5]");
    }

    @Test
    public void testFindEvenNumbersWithStream() {
        List<Integer> expectedEvenNumbers = Arrays.asList(2, 4, 6);
        List<Integer> actualEvenNumbers = FindEvenAndOdd.findEvenNumbersWithStream(NUMBERS);
        assertEquals(expectedEvenNumbers, actualEvenNumbers, "The even numbers should be [2, 4, 6]");
    }

    @Test
    public void testFindOddNumbersWithStream() {
        List<Integer> expectedOddNumbers = Arrays.asList(1, 3, 5);
        List<Integer> actualOddNumbers = FindEvenAndOdd.findOddNumbersWithStream(NUMBERS);
        assertEquals(expectedOddNumbers, actualOddNumbers, "The odd numbers should be [1, 3, 5]");
    }
}
