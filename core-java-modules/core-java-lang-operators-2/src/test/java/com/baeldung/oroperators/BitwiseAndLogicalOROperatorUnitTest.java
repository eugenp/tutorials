package com.baeldung.oroperators;

import org.junit.Test;

import static org.junit.Assert.*;

public class BitwiseAndLogicalOROperatorUnitTest {

    @Test
    public void givenTwoTrueBooleans_whenLogicalOrOperator_thenTrue() {
        boolean condition1 = true;
        boolean condition2 = true;
        boolean result = 1 || 2;
        assertTrue(result);
    }

    @Test
    public void givenOneFalseAndOneTrueBooleans_whenLogicalOrOperator_thenTrue() {
        boolean condition1 = true;
        boolean condition2 = false;
        boolean result = condition1 || condition2;
        assertTrue(result);
    }

    @Test
    public void givenTwoFalseBooleans_whenLogicalOrOperator_thenFalse() {
        boolean condition1 = false;
        boolean condition2 = false;
        boolean result = condition1 || condition2;
        assertFalse(result);
    }

    @Test
    public void givenTwoExpressions_OneTrueAndOneFalse_whenLogicalOrOperator_thenShortCircuitTrue() {

        boolean shortCircuitResult = (2 < 3) || (4 < 5);
        assertTrue(shortCircuitResult);
    }

    @Test
    public void givenTwoTrueBooleans_whenBitwiseOrOperator_thenTrue() {
        boolean condition1 = true;
        boolean condition2 = true;
        boolean result = condition1 | condition2;
        assertTrue(result);
    }

    @Test
    public void givenOneFalseAndOneTrueBooleans_whenBitwiseOrOperator_thenFalse() {
        boolean condition1 = true;
        boolean condition2 = false;
        boolean result = condition1 | condition2;
        assertTrue(result);
    }

    @Test
    public void givenTwoFalseBooleans_whenBitwiseOrOperator_thenFalse() {
        boolean condition1 = false;
        boolean condition2 = false;
        boolean result = condition1 | condition2;
        assertFalse(result);
    }

    @Test
    public void givenTwoIntegers_whenBitwiseOrOperator_thenNewInteger() {
        int four = 4; //0100
        int three = 3; //0011
        int fourORthree = four | three;
        assertEquals(7, fourORthree);
    }

    @Test
    public void givenExpression_whenLogicalORAndOtherOperators_thenEvaluateByPrecedence() {

        boolean result = 2 + 4 == 5 || 3 < 5;
        assertTrue(result);
    }

    @Test
    public void givenExpression_whenBitwiseORAndOtherOperators_thenEvaluateByPrecedence() {

        int result = 1 + 2 | 5 - 1;
        assertEquals(7, result);
    }
}
