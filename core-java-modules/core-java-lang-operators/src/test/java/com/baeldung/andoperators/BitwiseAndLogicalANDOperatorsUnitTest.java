package com.baeldung.andoperators;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class BitwiseAndLogicalANDOperatorsUnitTest {

    @Test
    public void givenTwoTrueBooleans_whenBitwiseAndOperator_thenTrue() {
        boolean a = true;
        boolean b = true;
        boolean result = a & b;
        assertTrue(result);
    }

    @Test
    public void givenOneFalseAndOneTrueBooleans_whenBitwiseAndOperator_thenFalse() {
        boolean a = false;
        boolean b = true;
        boolean result = a & b;
        assertFalse(result);
    }

    @Test
    public void givenTwoFalseBooleans_whenBitwiseAndOperator_thenFalse() {
        boolean a = false;
        boolean b = false;
        boolean result = a & b;
        assertFalse(result);
    }

    @Test
    public void givenTwoIntegers_whenBitwiseAndOperator_thenNewDecimalNumber() {
        int value1 = 6;
        int value2 = 5;
        int result = value1 & value2;
        assertEquals(4, result);
    }

    @Test
    public void givenTwoTrueBooleans_whenLogicalAndOperator_thenTrue() {
        boolean a = true;
        boolean b = true;
        boolean result = a && b;
        assertTrue(result);
    }

    @Test
    public void givenOneFalseAndOneTrueBooleans_whenLogicalAndOperator_thenFalse() {
        boolean a = false;
        boolean b = true;
        boolean result = a && b;
        assertFalse(result);
    }

    @Test
    public void givenTwoFalseBooleans_whenLogicalAndOperator_thenFalse() {
        boolean a = false;
        boolean b = false;
        boolean result = a && b;
        assertFalse(result);
    }

}
