package com.baeldung.algorithms.movingaverages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovingAverageWithApacheCommonsMathUnitTest {

    @Test
    public void whenInitialAverageIsCalculated_shouldReturnNAN() {
        MovingAverageWithApacheCommonsMath movingAverageCalculator = new MovingAverageWithApacheCommonsMath(5);
        assertEquals(Double.NaN, movingAverageCalculator.getMovingAverage(), 0.001);
    }

    @Test
    public void whenValuesAreAdded_shouldUpdateAverageCorrectly() {
        MovingAverageWithApacheCommonsMath movingAverageCalculator = new MovingAverageWithApacheCommonsMath(3);
        movingAverageCalculator.add(10);
        assertEquals(10.0, movingAverageCalculator.getMovingAverage(), 0.001);
        movingAverageCalculator.add(20);
        assertEquals(15.0, movingAverageCalculator.getMovingAverage(), 0.001);
        movingAverageCalculator.add(30);
        assertEquals(20.0, movingAverageCalculator.getMovingAverage(), 0.001);
    }

    @Test
    public void whenWindowSizeIsFull_shouldDropTheOldestValue() {
        MovingAverageWithApacheCommonsMath ma = new MovingAverageWithApacheCommonsMath(2);
        ma.add(10);
        ma.add(20);
        assertEquals(15.0, ma.getMovingAverage(), 0.001);
        ma.add(30);
        assertEquals(25.0, ma.getMovingAverage(), 0.001);
    }

}
