package com.baeldung.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CalculatorUnitTest {

    private Calculator calculator;

    @Before
    public void setUp() {
        this.calculator = new Calculator();
    }

    @Test
    public final void when_add_then_verifyResult() {
        assertEquals(5, calculator.add(2, 3), 0.1);
    }

    @Test
    public final void when_substract_then_verifyResult() {
        assertEquals(1, calculator.substract(3, 2), 0.1);
    }

    @Test
    public final void when_multiply_then_verifyResult() {
        assertEquals(6, calculator.multiply(2, 3), 0.1);
    }

    @Test
    public final void when_divide_then_verifyResult() {
        assertEquals(2, calculator.divide(6, 3), 0.1);
    }

}
