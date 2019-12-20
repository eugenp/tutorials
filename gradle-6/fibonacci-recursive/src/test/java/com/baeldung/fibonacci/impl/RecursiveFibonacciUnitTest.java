package com.baeldung.fibonacci.impl;

import com.baeldung.fibonacci.FibonacciSequenceGenerator;
import com.baeldung.fibonacci.FibonacciSequenceGeneratorFixture;

/**
 * Unit test which reuses the {@link FibonacciSequenceGeneratorFixture} test mix-in exported from
 * the fibonacci-spi project.
 */
final class RecursiveFibonacciUnitTest implements FibonacciSequenceGeneratorFixture {
    @Override public FibonacciSequenceGenerator provide() {
        return new RecursiveFibonacci();
    }
}
