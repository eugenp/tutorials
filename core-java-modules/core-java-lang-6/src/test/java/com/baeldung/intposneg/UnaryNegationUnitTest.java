package com.baeldung.intposneg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UnaryNegationUnitTest {
    @Test
    void whenUnaryOperatorUsed_thenChangeSign() {
        int x = 10;
        x = -x;
        assertEquals(x, -10);
        x = -x;
        assertEquals(x, 10);
        x = -x;
        assertEquals(x, -10);
    }

    @Test
    void whenTryCatchUsed_thenExceptionStringRemainsEmpty() {
        String exception = "";
        try {
            int x = Integer.MIN_VALUE;
            x = -x;
        } catch (ArithmeticException e) {
            exception = e.getMessage();
        }

        assertTrue(exception.isEmpty());
    }

    @Test
    void whenIntegerOverflow_thenNoExceptionThrown() {

        int x = Integer.MIN_VALUE;
        x = -x;
        assertEquals(x, Integer.MIN_VALUE);
        assertEquals(-x, Integer.MIN_VALUE);
        assertEquals(x, -x);
    }

    @Test
    void whenThereIsATemporaryVariable_thenChangeSign() {
        int x = 10;
        int y = -x;
        assertEquals(-y, x);
    }
}
