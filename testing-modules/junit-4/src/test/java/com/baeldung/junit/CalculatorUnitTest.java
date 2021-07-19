package com.baeldung.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CalculatorUnitTest {
    Calculator calculator = new Calculator();

    @Test
    public void testAddition() {
        assertEquals("addition", 8, calculator.add(5, 3));
    }
}
