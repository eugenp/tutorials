package com.baeldung.mcpannotations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorServiceUnitTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void givenTwoIntegers_whenAdd_thenReturnsCorrectSum() {
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = calculatorService.add(a, b);

        // Then
        assertEquals(30, result, "Sum should be 30");
    }
}