package com.baeldung;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovingAverageWithApacheCommonsMathTest {

    @Test
    public void when_initial_average_is_calculated_it_should_be_NAN() {
        MovingAverageWithApacheCommonsMath movingAverageCalculator = new MovingAverageWithApacheCommonsMath(5);
        assertEquals(Double.NaN, movingAverageCalculator.getMovingAverage(), 0.001);
    }

    @Test
    public void when_values_are_added_average_should_update_correctly() {
        MovingAverageWithApacheCommonsMath movingAverageCalculator = new MovingAverageWithApacheCommonsMath(3);
        movingAverageCalculator.add(10);
        assertEquals(10.0, movingAverageCalculator.getMovingAverage(), 0.001);
        movingAverageCalculator.add(20);
        assertEquals(15.0, movingAverageCalculator.getMovingAverage(), 0.001);
        movingAverageCalculator.add(30);
        assertEquals(20.0, movingAverageCalculator.getMovingAverage(), 0.001);
    }

    @Test
    public void when_window_size_is_full_oldest_value_should_be_dropped() {
        MovingAverageWithApacheCommonsMath ma = new MovingAverageWithApacheCommonsMath(2);
        ma.add(10);
        ma.add(20);
        assertEquals(15.0, ma.getMovingAverage(), 0.001);
        ma.add(30);
        assertEquals(25.0, ma.getMovingAverage(), 0.001); // 10 dropped, 20 and 30 remain
    }

}
