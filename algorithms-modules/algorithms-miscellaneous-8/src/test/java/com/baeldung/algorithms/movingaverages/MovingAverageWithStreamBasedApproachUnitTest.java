package com.baeldung.algorithms.movingaverages;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovingAverageWithStreamBasedApproachUnitTest {

    @Test
    public void whenEmptyDataIsPassed_shouldReturnZero() {
        double[] data = {};
        int windowSize = 3;
        double expectedAverage = 0;
        MovingAverageWithStreamBasedApproach calculator = new MovingAverageWithStreamBasedApproach(windowSize);
        double actualAverage = calculator.calculateAverage(data);
        assertEquals(expectedAverage, actualAverage);
    }

    @Test
    public void whenValidDataIsPassed_shouldReturnCorrectAverage() {
        double[] data = {10, 20, 30, 40, 50};
        int windowSize = 3;
        double expectedAverage = 40;
        MovingAverageWithStreamBasedApproach calculator = new MovingAverageWithStreamBasedApproach(windowSize);
        double actualAverage = calculator.calculateAverage(data);
        assertEquals(expectedAverage, actualAverage);
    }

    @Test
    public void whenValidDataIsPassedWithLongerWindowSize_shouldReturnCorrectAverage() {
        double[] data = {10, 20, 30, 40, 50};
        int windowSize = 5;
        double expectedAverage = 30;
        MovingAverageWithStreamBasedApproach calculator = new MovingAverageWithStreamBasedApproach(windowSize);
        double actualAverage = calculator.calculateAverage(data);
        assertEquals(expectedAverage, actualAverage);
    }
}