package com.baeldung.junit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubstractionUnitTest {
    Calculator calculator = new Calculator();

    @Test
    public void substraction() {
        assertEquals("substraction", 2, calculator.sub(5, 3));
    }
}
