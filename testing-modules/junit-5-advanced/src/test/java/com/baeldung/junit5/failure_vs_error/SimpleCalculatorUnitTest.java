package com.baeldung.junit5.failure_vs_error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

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
    @Disabled
    // disabled so that CI build still goes through
    void divideNumbers_failure(){
        double result = SimpleCalculator.divideNumbers(6, 3);
        assertEquals(15, result);
    }

    @Test
    @Disabled
    // disabled so that CI build still goes through
    void divideNumbers_error(){
        SimpleCalculator.divideNumbers(10, 0);
    }
}
