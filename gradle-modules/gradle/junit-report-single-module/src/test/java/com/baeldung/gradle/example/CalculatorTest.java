package com.baeldung.gradle.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void shouldAddTwoNumbers() {
        int result = calculator.add(5, 3);
        assertEquals(8, result);
    }

    @Test
    void shouldSubtractTwoNumbers() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result);
    }

    @Test
    void shouldThrowExceptionForDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
    }

    @Test
    void shouldMultiplyTwoNumbers() {
        int result = calculator.multiply(4, 7);
        assertEquals(28, result);
    }
}
