package com.baeldung.mcp.mcpserveroauth2;

import com.baeldung.mcp.mcpserveroauth2.model.CalculationResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceUnitTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void givenTwoNumbers_whenAdd_thenReturnsSum() {
        CalculationResult result = calculatorService.add(5.0, 3.0);
        assertEquals("addition", result.operation());
        assertEquals(5.0, result.operand1());
        assertEquals(3.0, result.operand2());
        assertEquals(8.0, result.result());
    }

    @Test
    void givenTwoNumbers_whenSubtract_thenReturnsDifference() {
        CalculationResult result = calculatorService.subtract(10.0, 4.0);
        assertEquals("subtraction", result.operation());
        assertEquals(10.0, result.operand1());
        assertEquals(4.0, result.operand2());
        assertEquals(6.0, result.result());
    }

    @Test
    void givenTwoNumbers_whenMultiply_thenReturnsProduct() {
        CalculationResult result = calculatorService.multiply(6.0, 7.0);
        assertEquals("multiplication", result.operation());
        assertEquals(6.0, result.operand1());
        assertEquals(7.0, result.operand2());
        assertEquals(42.0, result.result());
    }

    @Test
    void givenTwoNumbers_whenDivide_thenReturnsQuotient() {
        CalculationResult result = calculatorService.divide(15.0, 3.0);
        assertEquals("division", result.operation());
        assertEquals(15.0, result.operand1());
        assertEquals(3.0, result.operand2());
        assertEquals(5.0, result.result());
    }

    @Test
    void givenZeroDivisor_whenDivide_thenThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            calculatorService.divide(10.0, 0.0)
        );
        assertEquals("Cannot divide by zero", ex.getMessage());
    }

    @Test
    void givenNegativeNumbers_whenAdd_thenReturnsSum() {
        CalculationResult result = calculatorService.add(-2.0, -3.0);
        assertEquals(-5.0, result.result());
    }

    @Test
    void givenLargeNumbers_whenMultiply_thenReturnsProduct() {
        CalculationResult result = calculatorService.multiply(1e6, 1e6);
        assertEquals(1e12, result.result());
    }
}

