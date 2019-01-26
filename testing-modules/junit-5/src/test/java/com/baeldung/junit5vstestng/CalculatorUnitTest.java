package com.baeldung.junit5vstestng;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorUnitTest {

    @Test
    public void whenDividerIsZero_thenDivideByZeroExceptionIsThrown() {
        Calculator calculator = new Calculator();

        assertThrows(DivideByZeroException.class,
                () -> calculator.divide(10, 0));
    }

}