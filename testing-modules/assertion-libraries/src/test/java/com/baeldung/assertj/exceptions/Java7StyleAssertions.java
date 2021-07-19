package com.baeldung.assertj.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.Test;

public class Java7StyleAssertions {

    @Test
    public void whenDividingByZero_thenArithmeticException() {
        try {
            int numerator = 10;
            int denominator = 0;
            int quotient = numerator / denominator;
            fail("ArithmeticException expected because dividing by zero yields an ArithmeticException.");
            failBecauseExceptionWasNotThrown(ArithmeticException.class);
        } catch (Exception e) {
            assertThat(e).hasMessage("/ by zero");
            assertThat(e).isInstanceOf(ArithmeticException.class);
        }
    }
}
