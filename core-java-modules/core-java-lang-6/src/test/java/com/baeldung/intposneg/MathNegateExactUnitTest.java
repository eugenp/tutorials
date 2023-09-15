package com.baeldung.intposneg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MathNegateExactUnitTest {
    @Test
    void whenMathNegateExactUsed_thenChangeSign() {
        int x = 10;
        x = Math.negateExact(x);
        assertEquals( x, -10 );
        x = Math.negateExact(x);
        assertEquals( x, 10 );
        x = Math.negateExact(x);
        assertEquals( x, -10 );
    }

    @Test
    void whenArithmeticExceptionThrown_thenSetExceptionStringToNotEmpty() {
        String exception = "";
        try {
            int x = Integer.MIN_VALUE;
            x = Math.negateExact(x);
        } catch( ArithmeticException e ) {
            exception = e.getMessage();
        }
        assertFalse( exception.isEmpty() );
    }
}
