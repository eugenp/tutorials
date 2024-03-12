package com.baeldung;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExponentialMovingAverageTest {
    @Test(expected = IllegalArgumentException.class)
    public void when_alpha_is_invalid_should_throw_exception() {
        new ExponentialMovingAverage(0); // Alpha outside valid range
    }

    @Test
    public void when_first_value_is_added_EMA_should_be_same_as_value() {
        ExponentialMovingAverage ema = new ExponentialMovingAverage(0.5);
        assertEquals(10.0, ema.calculateEMA(10.0), 0.001);
    }

    @Test
    public void when_values_are_added_EMA_should_update_correctly() {
        ExponentialMovingAverage ema = new ExponentialMovingAverage(0.4);
        assertEquals(10.0, ema.calculateEMA(10.0), 0.001);
        assertEquals(14.0, ema.calculateEMA(20.0), 0.001);
        assertEquals(20.4, ema.calculateEMA(30.0), 0.001);
    }

    @Test
    public void when_alpha_is_closer_to_one_EMA_should_respond_faster_to_changes() {
        ExponentialMovingAverage ema1 = new ExponentialMovingAverage(0.2);
        ExponentialMovingAverage ema2 = new ExponentialMovingAverage(0.8);

        assertEquals(10.0, ema1.calculateEMA(10.0), 0.001);
        assertEquals(10.0, ema2.calculateEMA(10.0), 0.001);

        assertEquals(12.0, ema1.calculateEMA(20.0), 0.001); // Responds slower
        assertEquals(18.0, ema2.calculateEMA(20.0), 0.001); // Responds faster
    }
}
