package com.baeldung.failure_vs_error;

/**
 * @author paullatzelsperger
 * @since 2019-07-17
 */
public class SimpleCalculator {

    public static double divideNumbers(double dividend, double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return dividend / divisor;
    }
}
