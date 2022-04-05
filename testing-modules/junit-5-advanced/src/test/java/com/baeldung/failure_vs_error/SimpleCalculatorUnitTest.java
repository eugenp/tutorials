package com.baeldung.failure_vs_error;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author paullatzelsperger
 * @since 2019-07-17
 */
class SimpleCalculatorUnitTest {

    @Test
    void divideNumbers() {
        double result = SimpleCalculator.divideNumbers(6, 3);
        assertEquals(2, result);
    }

    @Test
    @Disabled("test is expected to fail, disabled so that CI build still goes through")
    void divideNumbers_failure() {
        double result = SimpleCalculator.divideNumbers(6, 3);
        assertEquals(15, result);
    }

    @Test
    @Disabled("test is expected to raise an error, disabled so that CI build still goes through")
    void divideNumbers_error() {
        SimpleCalculator.divideNumbers(10, 0);
    }
}
