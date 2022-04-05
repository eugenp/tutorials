package com.baeldung.divisionbyzero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DivisionByZeroUnitTest {

    @Test
    void givenInt_whenDividedByZero_thenThrowException() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 12 / 0;
        });
    }

    @Test
    void whenDividingIntZeroByZero_thenThrowException() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 0 / 0;
        });
    }

    @Test
    void whenDividingFloatingNumberByZero_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> {
            float result = 0f / 0;
        });
        assertDoesNotThrow(() -> {
            double result = 0d / 0;
        });
    }

    @Test
    void givenPositiveFloatingNumber_whenDividedByZero_thenReturnPositiveInfinity() {
        assertEquals(Float.POSITIVE_INFINITY, 12f / 0);
        assertEquals(Double.POSITIVE_INFINITY, 12d / 0);
    }

    @Test
    void givenNegativeFloatingNumber_whenDividedByZero_thenReturnNegativeInfinity() {
        assertEquals(Float.NEGATIVE_INFINITY, -12f / 0);
        assertEquals(Double.NEGATIVE_INFINITY, -12d / 0);
    }

    @Test
    void givenPositiveFloatingNumber_whenDividedByNegativeZero_thenReturnNegativeInfinity() {
        assertEquals(Float.NEGATIVE_INFINITY, 12f / -0f);
        assertEquals(Double.NEGATIVE_INFINITY, 12f / -0f);
    }

    @Test
    void whenDividingFloatingNumberZeroByZero_thenReturnNaN() {
        assertEquals(Float.NaN, 0f / 0);
        assertEquals(Double.NaN, 0d / 0);
    }

    @Test
    void givenABitRepresentationWithAllExponentBitsZeroesAndAllFractionBitsZeroes_whenTransformingItToFloat_thenReturnPositiveZero() {
        assertEquals(0f, Float.intBitsToFloat(0b00000000000000000000000000000000));
        assertEquals(-0f, Float.intBitsToFloat(0b10000000000000000000000000000000));
    }

    @Test
    void givenABitRepresentationWithAllExponentBitsOnesAndAllFractionBitsZeroes_whenTransformingItToFloat_thenReturnInfinity() {
        assertEquals(Float.POSITIVE_INFINITY, Float.intBitsToFloat(0b01111111100000000000000000000000));
        assertEquals(Float.NEGATIVE_INFINITY, Float.intBitsToFloat(0b11111111100000000000000000000000));
    }

    @Test
    void givenABitRepresentationWithAllExponentBitsOnesAndNotAllFractionBitsZeroes_whenTransformingItToFloat_thenReturnNan() {
        assertEquals(Float.NaN, Float.intBitsToFloat(0b11111111100000010000000000000000));
        assertEquals(Float.NaN, Float.intBitsToFloat(0b11111111100000011000000000100000));
        assertEquals(Float.NaN, Float.intBitsToFloat(0b11111111100000011100000000000000));
        assertEquals(Float.NaN, Float.intBitsToFloat(0b11111111100000011110000000000000));
    }
}
