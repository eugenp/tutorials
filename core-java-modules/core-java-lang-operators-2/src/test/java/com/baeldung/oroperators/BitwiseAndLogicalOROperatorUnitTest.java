package com.baeldung.oroperators;

import org.junit.Test;

import static org.junit.Assert.*;

public class BitwiseAndLogicalOROperatorUnitTest {

    @Test
    public void givenTwoTrueBooleans_whenLogicalOrOperator_thenTrue() {
        boolean condition1 = true;
        boolean condition2 = true;
        boolean result = condition1 || condition2;
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
    public void givenTwoExpressions_whenLogicalOrOperator_TrueOrFalse_thenShortCircuitTrue() {

        boolean shortCircuitResult = returnAndLog(true) || returnAndLog(false);
        assertTrue(shortCircuitResult);
    }

    @Test
    public void givenTwoExpressions_whenLogicalOrOperator_FalseOrTrue_thenShortCircuitTrue() {

        boolean shortCircuitResult = returnAndLog(false) || returnAndLog(true);
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
    public void givenFourIntegers_whenBitwiseOrOperator_thenNewInteger() {
        int result = 1 | 2 | 3 | 4;
        assertEquals(7, result);
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

    @Test
    public void givenExpression_multipleLogicalOr_thenEvaluateByLeftToRightAssociativity() {

        boolean result = true || false || false;
        assertTrue(result);
    }

    boolean returnAndLog(boolean value) {
        System.out.println("Returning " + value);
        return value;
    }
}
