package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class UnaryUnitTest {

    @Test
    public void whenUseTheOperatorMinusToChangeTheSignOfANumber() {

        int number = 3;
        int numberChangedToNegative = -(number);

        assertEquals(numberChangedToNegative, -3);

    }

    @Test
    public void whenUseTheOperatorPlusToGetASCIICode() {

        char charA = 'A';
        int intvalueofTheCharA = +charA;

        assertEquals(intvalueofTheCharA, 65);

    }

    @Test
    public void whenUseTheOperatorDecrement() {

        int number = 3;
        number--;

        assertEquals(number, 2);

        number++;

        assertEquals(number, 3);

    }

    @Test
    public void whenUseTheOperatorIncrement() {

        int number = 3;
        number++;

        assertEquals(number, 4);

    }

    @Test
    public void whenUseTheOperatorIncrementInTheMoment() {

        int number = 3;

        assertEquals(++number, 4);

    }

    @Test
    public void whenUseTheOperatorDecrementInTheMoment() {

        int number = 3;

        assertEquals(--number, 2);

    }

    @Test
    public void whenUseTheOperatorLogicalComplementWithBooleanVariable() {

        boolean allowed = true;

        assertEquals(!allowed, false);

    }

    @Test
    public void whenUseTheOperatorLogicalComplementWithComparison() {

        int number1 = 3;
        int number2 = 3;

        assertEquals(!(number1 == number2), false);

    }

}
