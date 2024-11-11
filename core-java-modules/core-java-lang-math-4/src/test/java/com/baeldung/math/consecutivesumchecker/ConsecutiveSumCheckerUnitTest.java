package com.baeldung.math.consecutivesumchecker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsecutiveSumCheckerUnitTest {
    int oddNumber = 21;
    int evenNumber = 10;

    public static int[] getConsecutiveNumbersSum(int number) {
        if (number % 2 == 0) {
            return null;
        }
        int n = (number - 1) / 2;
        return new int[]{n, n + 1};
    }

    @Test
    public void givenOddNumber_whenCheckingSumOfConsecutiveNumbers_thenReturnTrue() {
        boolean canBeExpressed = (oddNumber % 2 != 0);
        assertTrue(canBeExpressed);
    }

    @Test
    public void givenEvenNumber_whenCheckingSumOfConsecutiveNumbers_thenReturnFalse() {
        boolean canBeExpressed = (evenNumber % 2 != 0);
        assertFalse(canBeExpressed);
    }


    @Test
    public void givenOddNumber_whenUsingMathFormula_thenReturnConsecutiveNumbers() {
        int[] result = getConsecutiveNumbersSum(oddNumber);
        assertNotNull(result);
        assertArrayEquals(new int[]{10, 11}, result);
    }

    @Test
    public void givenEvenNumber_whenUsingMathFormula_thenReturnNull() {
        int[] result = getConsecutiveNumbersSum(evenNumber);
        assertNull(result);
    }
}
