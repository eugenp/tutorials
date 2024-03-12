package com.baeldung;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovingAverageByCircularBufferTest {

@Test
public void when_initial_average_is_calculated_it_should_be_zero() {
    MovingAverageByCircularBuffer ma = new MovingAverageByCircularBuffer(5);
    assertEquals(Double.NaN, ma.getMovingAverage(), 0.001); // Empty buffer
}

@Test
public void when_values_are_added_average_should_update_correctly() {
    MovingAverageByCircularBuffer ma = new MovingAverageByCircularBuffer(3);
    ma.add(10);
    assertEquals(10.0, ma.getMovingAverage(), 0.001);
    ma.add(20);
    assertEquals(15.0, ma.getMovingAverage(), 0.001);
    ma.add(30);
    assertEquals(20.0, ma.getMovingAverage(), 0.001);
}

@Test
public void when_window_size_is_full_oldest_value_should_be_overwritten() {
    MovingAverageByCircularBuffer ma = new MovingAverageByCircularBuffer(2);
    ma.add(10);
    ma.add(20);
    assertEquals(15.0, ma.getMovingAverage(), 0.001);
    ma.add(30); // Overwrites 10
    assertEquals(25.0, ma.getMovingAverage(), 0.001);
}
}
