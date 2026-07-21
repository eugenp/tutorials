package com.baeldung.junit5.failurethreshold;

import org.junit.jupiter.api.RepeatedTest;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FailureThresholdUnitTest {
    Random random = new Random();

    @RepeatedTest(value = 10, failureThreshold = 2)
    void givenRandomNumberGenerator_whenGeneratingRandomNumber_thenNumberShouldBeWithinRange() {
        int number = random.nextInt(10);
        assertTrue(number <= 9);
    }
}
