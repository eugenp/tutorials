package com.baeldung.fibonacci;

/**
 * Describes an SPI for a Fibonacci sequence generator function.
 */
public interface FibonacciSequenceGenerator {

    /**
     * @param nth the index of the number in the fibonacci sequence
     * @return the nth number in the fibonacci sequence
     */
    int generate(int nth);
}
