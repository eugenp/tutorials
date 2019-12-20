package com.baeldung.fibonacci;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Reusable test fixture for {@link FibonacciSequenceGenerator} implementations. Tests will be
 * skipped if no such implementation exists.
 */
public interface FibonacciSequenceGeneratorFixture {

    /** @return the implementation of {@link FibonacciSequenceGenerator} to test. Must not be null */
    FibonacciSequenceGenerator provide();

    @Test default void when_sequence_index_is_negative_then_throws() {
        final FibonacciSequenceGenerator generator = provide();
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-1));
    }

    @Test default void when_given_index_then_generates_fibonacci_number() {
        final FibonacciSequenceGenerator generator = provide();
        final int[] sequence = { 0, 1, 1, 2, 3, 5, 8 };
        for (int i = 0; i < sequence.length; i++) {
            assertEquals(sequence[i], generator.generate(i));
        }
    }
}
