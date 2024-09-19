package com.baeldung.teavm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorUnitTest {

    @Test
    public void whenAddingTwoPositiveNumbers_thenCorrectSum() {
        // when
        int result = Calculator.sum(3, 7);

        // then
        assertEquals(10, result);
    }

    @Test
    public void whenAddingNegativeAndPositiveNumber_thenCorrectSum() {
        // when
        int result = Calculator.sum(-5, 10);

        // then
        assertEquals(5, result);
    }

    @Test
    public void whenAddingTwoNegativeNumbers_thenCorrectSum() {
        // when
        int result = Calculator.sum(-4, -6);

        // then
        assertEquals(-10, result);
    }
}
