package com.baeldung.bitwiseoperator.test;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

public class BitwiseOperatorUnitTest {

    @Test
    public void givenTwoIntegers_whenAndOperator_thenNewDecimalNumber() {
        int value1 = 6;
        int value2 = 5;
        int result = value1 & value2;
        assertEquals(result, 4);
    }

    @Test
    public void givenTwoIntegers_whenOrOperator_thenNewDecimalNumber() {
        int value1 = 6;
        int value2 = 5;
        int result = value1 | value2;
        assertEquals(result, 7);
    }

    @Test
    public void givenTwoIntegers_whenXorOperator_thenNewDecimalNumber() {
        int value1 = 6;
        int value2 = 5;
        int result = value1 ^ value2;
        assertEquals(result, 3);
    }

    @Test
    public void givenOneInteger_whenNotOperator_thenNewDecimalNumber() {
        int value1 = 6;
        int result = ~value1;
        assertEquals(result, -7);
    }

    @Test
    public void givenOnePositiveInteger_whenSignedRightShiftOperator_thenNewDecimalNumber() {
        int value = 12;
        int rightShift = value >> 2;
        assertEquals(rightShift, 3);
    }

    @Test
    public void givenOneNegativeInteger_whenSignedRightShiftOperator_thenNewDecimalNumber() {
        int value = -12;
        int rightShift = value >> 2;
        assertEquals(rightShift, -3);
    }

    @Test
    public void givenOnePositiveInteger_whenLeftShiftOperator_thenNewDecimalNumber() {
        int value = 12;
        int leftShift = value << 2;
        assertEquals(leftShift, 48);
    }

    @Test
    public void givenOneNegativeInteger_whenLeftShiftOperator_thenNewDecimalNumber() {
        int value = -12;
        int leftShift = value << 2;
        assertEquals(leftShift, -48);
    }

    @Test
    public void givenOnePositiveInteger_whenUnsignedRightShiftOperator_thenNewDecimalNumber() {
        int value = 12;
        int unsignedRightShift = value >>> 2;
        assertEquals(unsignedRightShift, 3);
    }

    @Test
    public void givenOneNegativeInteger_whenUnsignedRightShiftOperator_thenNewDecimalNumber() {
        int value = -12;
        int unsignedRightShift = value >>> 2;
        assertEquals(unsignedRightShift, 1073741821);
    }

}
