package com.baeldung.junit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdditionUnitTest {
    Calculator calculator = new Calculator();

    @Test
    public void testAddition() {
        assertEquals("addition", 8, calculator.add(5, 3));
    }
}
