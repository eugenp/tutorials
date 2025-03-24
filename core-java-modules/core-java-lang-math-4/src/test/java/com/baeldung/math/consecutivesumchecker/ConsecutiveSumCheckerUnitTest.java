package com.baeldung.math.consecutivesumchecker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsecutiveSumCheckerUnitTest {
    int oddNumber = 21;
    int evenNumber = 10;

    public static int[] getConsecutiveNumbersSum(int number) {
        if (number % 2 == 0) {
            return new int[0];
        }
        int n = (number - 1) / 2;
        return new int[]{n, n + 1};
    }
    
    public boolean isOdd(int number) {
        return number % 2 != 0;
    }
    
    public static List<int[]> findConsecutiveSums(int number) {
        List<int[]> results = new ArrayList<>();
        for (int k = 2; k * (k - 1) / 2 < number; k++) {
            int sumOfFirstKMinus1Numbers = k * (k - 1) / 2;
            if ((number - sumOfFirstKMinus1Numbers) % k == 0) {
                int n = (number - sumOfFirstKMinus1Numbers) / k;
                if (n > 0) {
                    int[] sequence = new int[k];
                    for (int i = 0; i < k; i++) {
                        sequence[i] = n + i;
                    }
                    results.add(sequence);
                }
            }
        }
        return results;
    }

    @Test
    public void givenOddNumber_whenCheckingSumOfConsecutiveNumbers_thenReturnTrue() {
        assertTrue(isOdd(oddNumber));
    }

    @Test
    public void givenEvenNumber_whenCheckingSumOfConsecutiveNumbers_thenReturnFalse() {
        assertFalse(isOdd(evenNumber));
    }

    @Test
    public void givenEvenNumber_whenUsingMathFormula_thenReturnNull() {
        int[] result = getConsecutiveNumbersSum(10);
        assertArrayEquals(new int[0], result);
    }

    @Test
    public void givenOddNumber_whenUsingMathFormula_thenReturnConsecutiveNumbers() {
        int[] result = getConsecutiveNumbersSum(oddNumber);
        assertNotNull(result);
        assertArrayEquals(new int[]{10, 11}, result);
    }

    @Test
    public void givenNumber_whenFindingConsecutiveSums_thenReturnValidSequences() {
        List<int[]> oddResults = findConsecutiveSums(oddNumber);
        assertEquals(3, oddResults.size());
        assertArrayEquals(new int[]{10, 11}, oddResults.get(0));
        assertArrayEquals(new int[]{6, 7, 8}, oddResults.get(1));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, oddResults.get(2));
        List<int[]> evenResults = findConsecutiveSums(evenNumber);
        assertEquals(1, evenResults.size());
        assertArrayEquals(new int[]{1, 2, 3, 4}, evenResults.get(0));
    }
}
