package com.baeldung.numbersinrange;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RandomNumbersInARangeUnitTest {

    @Test
    public void givenTheRange1To10_whenUsingMathRandom_thenExpectCorrectResult() {
        RandomNumbersInARange randomNumbersInARange = new RandomNumbersInARange();
        int number = randomNumbersInARange.getRandomNumber(1, 10);

        assertTrue(number >= 1);
        assertTrue(number < 10);
    }

    @Test
    public void givenTheRange1To10_whenUsingRandomInts_thenExpectCorrectResult() {
        RandomNumbersInARange randomNumbersInARange = new RandomNumbersInARange();
        int number = randomNumbersInARange.getRandomNumberUsingInts(1, 10);

        assertTrue(number >= 1);
        assertTrue(number < 10);
    }

    @Test
    public void givenTheRange1To10_whenUsingRandomNextInt_thenExpectCorrectResult() {
        RandomNumbersInARange randomNumbersInARange = new RandomNumbersInARange();
        int number = randomNumbersInARange.getRandomNumberUsingNextInt(1, 10);

        assertTrue(number >= 1);
        assertTrue(number < 10);
    }

    @Test
    public void givenTheRange1To10_whenUsingThreadLocalRandom_thenExpectCorrectResult() {
        RandomNumbersInARange randomNumbersInARange = new RandomNumbersInARange();
        int number = randomNumbersInARange.getRandomNumberUsingThreadLocalRandom(1, 10);

        assertTrue(number >= 1);
        assertTrue(number < 10);
    }
}
