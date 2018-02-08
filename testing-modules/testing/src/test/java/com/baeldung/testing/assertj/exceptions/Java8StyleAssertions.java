package com.baeldung.testing.assertj.exceptions;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;

public class Java8StyleAssertions {

    @Test
    public void whenDividingByZero_thenArithmeticException() {
        assertThatThrownBy(() -> {
            int numerator = 10;
            int denominator = 0;
            int quotient = numerator / denominator;
        }).isInstanceOf(ArithmeticException.class)
            .hasMessageContaining("/ by zero");

        assertThatExceptionOfType(ArithmeticException.class).isThrownBy(() -> {
            int numerator = 10;
            int denominator = 0;
            int quotient = numerator / denominator;
        })
            .withMessageContaining("/ by zero");

        // BDD style:

        // when
        Throwable thrown = catchThrowable(() -> {
            int numerator = 10;
            int denominator = 0;
            int quotient = numerator / denominator;
        });

        // then
        assertThat(thrown).isInstanceOf(ArithmeticException.class)
            .hasMessageContaining("/ by zero");

    }
}
