package com.baeldung.currenttimemillisandnanotime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentTimeMillisAndNanoTimeUnitTest {
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

        assertTrue(duration >= 0);
    }

    @Test
    public void givenShortTaskInProgress_whenMeasuringShortDuration_thenDurationShouldBeNonNegative() {
        long startNanoTime = System.nanoTime();

        performShortTask();

        long endNanoTime = System.nanoTime();
        long duration = endNanoTime - startNanoTime;

        assertTrue(duration >= 0);
    }
}
