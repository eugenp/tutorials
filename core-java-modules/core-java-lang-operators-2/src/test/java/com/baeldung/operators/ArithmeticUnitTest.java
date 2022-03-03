package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class ArithmeticUnitTest {

    @Test
    void givenASentenece_WhenComparedWithConcatenadeSentenceUsingArithmeticAddition_thenSuccess() {
        String sentence = "Hello world!";
        String wordsConcatenated = "Hello " + "world" + "!";
        assertEquals(sentence, wordsConcatenated);
    }

    @Test
    void whenPlusTwoNumbersUsingTheOperatorArithmeticAddition_thenSuccess() {
        int resultSumPositive = 10 + 5;
        assertEquals(resultSumPositive, 15);
    }

    @Test
    void whenSubtractTwoNumbersUsingTheOperatorArithmeticSubtraction_thenSuccess() {
        int resultSubtractionPositive = 10 - 5;
        assertEquals(resultSubtractionPositive, 5);
    }

    @Test
    void whenMultiplyTwoNumbersUsingTheOperatorArithmeticMultiplication_thenSuccess() {
        int resultMultiplicationPositive = 10 * 5;
        assertEquals(resultMultiplicationPositive, 50);
    }

    @Test
    void whenDivideTwoNumbersUsingTheOperatorArithmeticDivision_thenSuccess() {
        int resultDivisionPositive = 10 / 5;
        assertEquals(resultDivisionPositive, 2);
    }

    @Test
    void whenFindTheRestBetweenTwoNumbersUsingTheOperatorArithmeticRemainder_thenSuccess() {
        int resultDivisionPositive = 10 % 5;
        assertEquals(resultDivisionPositive, 0);
    }
}
