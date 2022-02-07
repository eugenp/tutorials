package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class UnaryUnitTest {

    @Test
    void whenUseTheOperatorMinusToChangeTheSignOfANumber_thenSuccess() {
        int number = 3;
        int numberChangedToNegative = -(number);
        assertEquals(numberChangedToNegative, -3);
    }

    @Test
    void whenUseTheOperatorPlusToGetASCIICode_thenSuccess() {
        char charA = 'A';
        int intvalueofTheCharA = +charA;
        assertEquals(intvalueofTheCharA, 65);
    }

    @Test
    void whenUseTheOperatorDecrement_thenSuccess() {
        int number = 3;
        number--;
        assertEquals(number, 2);
        number++;
        assertEquals(number, 3);
    }

    @Test
    void whenUseTheOperatorIncrement_thenSuccess() {
        int number = 3;
        number++;
        assertEquals(number, 4);
    }

    @Test
    void whenUseTheOperatorIncrementInTheMoment_thenSuccess() {
        int number = 3;
        assertEquals(++number, 4);
    }

    @Test
    void whenUseTheOperatorDecrementInTheMoment_thenSuccess() {
        int number = 3;
        assertEquals(--number, 2);
    }

    @Test
    void whenUseTheOperatorLogicalComplementWithBooleanVariable_thenSuccess() {
        boolean allowed = true;
        assertEquals(!allowed, false);
    }

    @Test
    void whenUseTheOperatorLogicalComplementWithComparison_thenSuccess() {
        int number1 = 3;
        int number2 = 3;
        assertEquals(!(number1 == number2), false);
    }
}