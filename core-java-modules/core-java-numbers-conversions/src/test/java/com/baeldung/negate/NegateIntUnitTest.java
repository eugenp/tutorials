package com.baeldung.negate;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class NegateIntUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(NegateIntUnitTest.class);

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
    void givenIntMinValue_whenUsingUnaryMinusOperator_thenCannotGetExpectedResult() {
        int min = Integer.MIN_VALUE;
        LOG.info("The value of '-min' is: " + -min);

        assertTrue((-min) < 0);
    }

    @Test
    void givenIntMinValue_whenUsingBitwiseComplementOperator_thenCannotGetExpectedResult() {
        int min = Integer.MIN_VALUE;
        int result = ~min + 1;
        LOG.info("The value of '~min + 1' is: " + result);

        assertTrue(result < 0);
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