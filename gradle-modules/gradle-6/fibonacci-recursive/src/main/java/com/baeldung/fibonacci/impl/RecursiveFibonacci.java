package com.baeldung.fibonacci.impl;

import com.baeldung.fibonacci.FibonacciSequenceGenerator;
import com.google.auto.service.AutoService;

/**
 * Recursive implementation of the {@link FibonacciSequenceGenerator}.
 */
@AutoService(FibonacciSequenceGenerator.class) public final class RecursiveFibonacci implements FibonacciSequenceGenerator {

    @Override public int generate(int nth) {
        if (nth < 0) {
            throw new IllegalArgumentException("sequence number must be 0 or greater");
        }
        if (nth <= 1) {
            return nth;
        }
        return generate(nth - 1) + generate(nth - 2);
    }
}
