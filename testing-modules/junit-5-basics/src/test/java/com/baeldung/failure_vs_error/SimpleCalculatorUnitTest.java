package com.baeldung.failure_vs_error;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author paullatzelsperger
 * @since 2019-07-17
 */
class SimpleCalculatorUnitTest {

    @Test
    void whenDivideByValidNumber_thenAssertCorrectResult() {
        double result = SimpleCalculator.divideNumbers(6, 3);
        assertEquals(2, result);
    }

    @Test
    @Disabled("test is expected to fail, disabled so that CI build still goes through")
    void whenDivideNumbers_thenExpectWrongResult() {
        double result = SimpleCalculator.divideNumbers(6, 3);
        assertEquals(15, result);
    }

    @Test
    @Disabled("test is expected to raise an error, disabled so that CI build still goes through")
    void whenDivideByZero_thenThrowsException() {
        SimpleCalculator.divideNumbers(10, 0);
    }

    @Test
    void whenDivideByZero_thenAssertException(){
        assertThrows(ArithmeticException.class, () -> SimpleCalculator.divideNumbers(10, 0));
    }

}
