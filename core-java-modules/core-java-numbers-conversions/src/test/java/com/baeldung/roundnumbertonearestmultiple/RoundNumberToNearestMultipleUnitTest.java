package com.baeldung.roundnumbertonearestmultiple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoundNumberToNearestMultipleUnitTest {

    public static int originalNumber = 18;
    public static int expectedRoundedNumber = 20;
    public static int nearest = 5;

    @Test
    public void givenNumber_whenUsingBasicMathOperations_thenRoundUpToNearestMultipleOf5() {

        int roundedNumber = (originalNumber % nearest == 0) ? originalNumber : ((originalNumber / nearest) + 1) * nearest;
        assertEquals(expectedRoundedNumber, roundedNumber);
    }

    @Test
    public void givenNumber_whenUsingMathCeil_thenRoundUpToNearestMultipleOf5() {

        int roundedNumber = (int) (Math.ceil(originalNumber / (float) (nearest)) * nearest);
        assertEquals(expectedRoundedNumber, roundedNumber);
    }

    @Test
    public void givenNumber_whenUsingMathRound_thenRoundUpToNearestMultipleOf5() {

        int roundedNumber = Math.round(originalNumber / (float) (nearest)) * nearest;
        assertEquals(expectedRoundedNumber, roundedNumber);
    }

    @Test
    public void givenNumber_whenUsingMathFloor_thenRoundUpToNearestMultipleOf5() {

        int roundedNumber = (int) (Math.floor((double) (originalNumber + nearest / 2) / nearest) * nearest);
        assertEquals(expectedRoundedNumber, roundedNumber);
    }

}
