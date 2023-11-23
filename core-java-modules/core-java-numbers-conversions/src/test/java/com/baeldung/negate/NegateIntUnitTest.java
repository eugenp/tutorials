package com.baeldung.negate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NegateIntUnitTest {
    @Test
    void whenUsingUnaryMinusOperator_thenGetExpectedResult() {
        int x = 42;
        assertEquals(-42, -x);

        int z = 0;
        assertEquals(0, -z);

        int n = -42;
        assertEquals(42, -n);
    }

    @Test
    void whenUsingBitwiseComplementOperator_thenGetExpectedResult() {
        int x = 42;
        assertEquals(-42, ~x + 1);

        int z = 0;
        assertEquals(0, ~z + 1);

        int n = -42;
        assertEquals(42, ~n + 1);
    }

    @Test
    void whenUsingUnaryMinusOperatorWithMinInt_thenCannotGetExpectedResult() {
        int min = Integer.MIN_VALUE;
        System.out.println("The value of '-min' is: " + -min);
        assertFalse((-min) > 0);
    }

    @Test
    void whenUsingBitwiseComplementOperatorWithMinInt_thenCannotGetExpectedResult() {
        int min = Integer.MIN_VALUE;
        int result = ~min + 1;
        System.out.println("The value of '~min + 1' is: " + result);
        assertFalse(result > 0);
    }


    @Test
    void whenUsingUnaryMinusOperatorWithMinInt_thenGetExpectedResult() {
        int x = 42;
        assertEquals(-42, Math.negateExact(x));

        int z = 0;
        assertEquals(0, Math.negateExact(z));

        int n = -42;
        assertEquals(42, Math.negateExact(n));

        int min = Integer.MIN_VALUE;
        assertThrowsExactly(ArithmeticException.class, () -> Math.negateExact(min));
    }
}