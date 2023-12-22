package com.baeldung.currenttimemillisandnanotime;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentTimeMillisAndNanoTimeUnitTest {
    Logger logger
            = Logger.getLogger(
            CurrentTimeMillisAndNanoTimeUnitTest.class.getName());

    private static void performShortTask() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void performTask() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTaskInProgress_whenMeasuringTimeDuration_thenDurationShouldBeNonNegative() {
        long startTime = System.currentTimeMillis();

        performTask();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logger.info("Task duration: " + duration + " milliseconds");
        assertTrue(duration >= 0);
    }

    @Test
    public void givenShortTaskInProgress_whenMeasuringShortDuration_thenDurationShouldBeNonNegative() {
        long startNanoTime = System.nanoTime();

        performShortTask();

        long endNanoTime = System.nanoTime();
        long duration = endNanoTime - startNanoTime;

        logger.info("Short task duration: " + duration + " nanoseconds");
        assertTrue(duration >= 0);
    }
}
