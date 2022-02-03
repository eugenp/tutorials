package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ArithmeticUnitTest {

    @Test
    void whenUseTheOperatorArithmeticAdditionToConcatenate() {

        String sentence = "Hello world!";
        String wordsConcatenated = "Hello " + "world" + "!";

        assertEquals(sentence, wordsConcatenated);

    }

    @Test
    public void whenUseTheOperatorArithmeticAdditionToOperation() {

        int resultSumPositive = 10 + 5;

        assertEquals(resultSumPositive, 15);

    }

    @Test
    public void whenUseTheOperatorSubtraction() {

        int resultSubtractionPositive = 10 - 5;

        assertEquals(resultSubtractionPositive, 5);

    }

    @Test
    public void whenUseTheOperatorMultiplication() {

        int resultMultiplicationPositive = 10 * 5;

        assertEquals(resultMultiplicationPositive, 50);

    }

    @Test
    public void whenUseTheOperatorDivision() {

        int resultDivisionPositive = 10 / 5;

        assertEquals(resultDivisionPositive, 2);

    }

    @Test
    public void whenUseTheOperatorRemainder() {

        int resultDivisionPositive = 10 % 5;

        assertEquals(resultDivisionPositive, 0);

    }

}
