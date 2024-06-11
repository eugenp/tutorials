package com.baeldung.algorithms.movingaverages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovingAverageByCircularBufferUnitTest {

    @Test
    public void whenInitialAverageIsCalculated_shouldReturnNAN() {
        MovingAverageByCircularBuffer ma = new MovingAverageByCircularBuffer(5);
        assertEquals(Double.NaN, ma.getMovingAverage(), 0.001);
    }

    @Test
    public void whenValuesAreAdded_shouldUpdateAverageCorrectly() {
        MovingAverageByCircularBuffer ma = new MovingAverageByCircularBuffer(3);
        ma.add(10);
        assertEquals(10.0, ma.getMovingAverage(), 0.001);
        ma.add(20);
        assertEquals(15.0, ma.getMovingAverage(), 0.001);
        ma.add(30);
        assertEquals(20.0, ma.getMovingAverage(), 0.001);
    }

    @Test
    public void whenWindowSizeIsFull_shouldOverrideTheOldestValue() {
        MovingAverageByCircularBuffer ma = new MovingAverageByCircularBuffer(2);
        ma.add(10);
        ma.add(20);
        assertEquals(15.0, ma.getMovingAverage(), 0.001);
        ma.add(30);
        assertEquals(25.0, ma.getMovingAverage(), 0.001);
    }
}
