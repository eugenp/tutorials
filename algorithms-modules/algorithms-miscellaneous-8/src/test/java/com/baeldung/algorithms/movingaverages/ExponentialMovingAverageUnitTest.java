package com.baeldung.algorithms.movingaverages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExponentialMovingAverageUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenAlphaIsInvalid_shouldThrowException() {
        new ExponentialMovingAverage(0);
    }

    @Test
    public void whenFirstValueIsAdded_shouldHaveExponentialMovingAverageSameAsValue() {
        ExponentialMovingAverage ema = new ExponentialMovingAverage(0.5);
        assertEquals(10.0, ema.calculateEMA(10.0), 0.001);
    }

    @Test
    public void whenValuesAreAdded_shouldUpdateExponentialMovingAverageCorrectly() {
        ExponentialMovingAverage ema = new ExponentialMovingAverage(0.4);
        assertEquals(10.0, ema.calculateEMA(10.0), 0.001);
        assertEquals(14.0, ema.calculateEMA(20.0), 0.001);
        assertEquals(20.4, ema.calculateEMA(30.0), 0.001);
    }

    @Test
    public void whenAlphaIsCloserToOne_exponentialMovingAverageShouldRespondFasterToChanges() {
        ExponentialMovingAverage ema1 = new ExponentialMovingAverage(0.2);
        ExponentialMovingAverage ema2 = new ExponentialMovingAverage(0.8);

        assertEquals(10.0, ema1.calculateEMA(10.0), 0.001);
        assertEquals(10.0, ema2.calculateEMA(10.0), 0.001);

        assertEquals(12.0, ema1.calculateEMA(20.0), 0.001);
        assertEquals(18.0, ema2.calculateEMA(20.0), 0.001);
    }
}
