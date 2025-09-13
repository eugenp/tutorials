package com.baeldung.failsafe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeException;
import dev.failsafe.RetryPolicy;

public class RetryUnitTest {
    @Test
    void defaultRetryTwice() {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder().build();

        AtomicInteger counter = new AtomicInteger(2);

        Integer result = Failsafe.with(retryPolicy)
            .get(() -> {
                if (counter.decrementAndGet() > 0) {
                    throw new IOException("Something went wrong");
                }

                return 2;
            });

        assertEquals(0, counter.get());
        assertEquals(2, result);
    }

    @Test
    void defaultRetryFourTimes() {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder().build();

        AtomicInteger counter = new AtomicInteger(4);

        FailsafeException exception = assertThrows(FailsafeException.class, () ->
            Failsafe.with(retryPolicy)
                .get(() -> {
                    if (counter.decrementAndGet() > 0) {
                        throw new IOException("Something went wrong");
                    }

                    return 2;
                })
        );

        assertEquals(1, counter.get());
        assertInstanceOf(IOException.class, exception.getCause());
        assertEquals("Something went wrong", exception.getCause().getMessage());
    }


    @Test
    void retryFourTimes() {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
            .withMaxAttempts(5)
            .build();

        AtomicInteger counter = new AtomicInteger(4);

        Integer result = Failsafe.with(retryPolicy)
            .get(() -> {
                if (counter.decrementAndGet() > 0) {
                    throw new IOException("Something went wrong");
                }

                return 2;
            });

        assertEquals(0, counter.get());
        assertEquals(2, result);
    }

    @Test
    void retryWithDelay() {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
            .withDelay(Duration.ofMillis(250))
            .build();

        AtomicInteger counter = new AtomicInteger(2);

        Integer result = Failsafe.with(retryPolicy)
            .get(() -> {
                System.out.println("Retrying: " + System.currentTimeMillis());

                if (counter.decrementAndGet() > 0) {
                    throw new IOException("Something went wrong");
                }

                return 2;
            });

        assertEquals(0, counter.get());
        assertEquals(2, result);
    }


    @Test
    void retryWithBackoff() {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
            .withMaxAttempts(20)
            .withBackoff(Duration.ofMillis(100), Duration.ofMillis(2000))
            .build();

        AtomicInteger counter = new AtomicInteger(10);

        Integer result = Failsafe.with(retryPolicy)
            .get(() -> {
                System.out.println("Retrying: " + System.currentTimeMillis());

                if (counter.decrementAndGet() > 0) {
                    throw new IOException("Something went wrong");
                }

                return 2;
            });

        assertEquals(0, counter.get());
        assertEquals(2, result);
    }
}
