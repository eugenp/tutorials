package com.baeldung.andoperators;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class BitwiseAndLogicalANDOperatorsUnitTest {

    @Test
    public void givenTwoTrueBooleans_whenBitwiseAndOperator_thenTrue() {
        boolean trueBool = true;
        boolean anotherTrueBool = true;
        boolean trueANDTrue = trueBool & anotherTrueBool;
        assertTrue(trueANDTrue);
    }

    @Test
    public void givenOneFalseAndOneTrueBooleans_whenBitwiseAndOperator_thenFalse() {
        boolean trueBool = true;
        boolean falseBool = false;
        boolean trueANDFalse = trueBool & falseBool;
        assertFalse(trueANDFalse);
    }

    @Test
    public void givenTwoFalseBooleans_whenBitwiseAndOperator_thenFalse() {
        boolean falseBool = false;
        boolean anotherFalseBool = false;
        boolean falseANDFalse = falseBool & anotherFalseBool;
        assertFalse(falseANDFalse);
    }

    @Test
    public void givenTwoIntegers_whenBitwiseAndOperator_thenNewDecimalNumber() {
        int six = 6;
        int five = 5;
        int shouldBeFour = six & five;
        assertEquals(4, shouldBeFour);
    }

    @Test
    public void givenTwoTrueBooleans_whenLogicalAndOperator_thenTrue() {
        boolean trueBool = true;
        boolean anotherTrueBool = true;
        boolean trueANDTrue = trueBool && anotherTrueBool;
        assertTrue(trueANDTrue);
    }

    @Test
    public void givenOneFalseAndOneTrueBooleans_whenLogicalAndOperator_thenFalse() {
        boolean trueBool = true;
        boolean falseBool = false;
        boolean trueANDFalse = trueBool && falseBool;
        assertFalse(trueANDFalse);
    }

    @Test
    public void givenTwoFalseBooleans_whenLogicalAndOperator_thenFalse() {
        boolean falseBool = false;
        boolean anotherFalseBool = false;
        boolean falseANDFalse = falseBool && anotherFalseBool;
        assertFalse(falseANDFalse);
    }

    @Test
    public void givenTwoFalseExpressions_whenLogicalAndOperator_thenShortCircuitFalse() {
        boolean shortCircuitResult = (2<1) && (4<5);
        assertFalse(shortCircuitResult);
    }

}
