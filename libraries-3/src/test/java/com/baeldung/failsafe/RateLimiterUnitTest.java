package com.baeldung.failsafe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;
import dev.failsafe.RateLimitExceededException;
import dev.failsafe.RateLimiter;

public class RateLimiterUnitTest {
    @Test
    void lessThanWindow() {
        RateLimiter<Object> rateLimiter = RateLimiter.burstyBuilder(10, Duration.ofSeconds(2))
            .build();

        FailsafeExecutor<Object> executor = Failsafe.with(rateLimiter);

        for (int i = 0; i < 5; ++i) {
            int expected = i;
            assertEquals(expected, executor.get(() -> expected));
        }
    }

    @Test
    void moreThanWindow() {
        RateLimiter<Object> rateLimiter = RateLimiter.burstyBuilder(10, Duration.ofSeconds(2))
            .build();

        FailsafeExecutor<Object> executor = Failsafe.with(rateLimiter);

        for (int i = 0; i < 10; ++i) {
            int expected = i;
            assertEquals(expected, executor.get(() -> expected));
        }

        assertThrows(RateLimitExceededException.class, () -> executor.run(() -> {}));
    }

    @Test
    void executedAfterBurstyWindow() throws InterruptedException {
        RateLimiter<Object> rateLimiter = RateLimiter.burstyBuilder(10, Duration.ofSeconds(2)).build();

        FailsafeExecutor<Object> executor = Failsafe.with(rateLimiter);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            int expected = i;
            System.out.println("Time " + i + ": " + (System.currentTimeMillis() - start));
            assertEquals(expected, executor.get(() -> expected));
            Thread.sleep(100);
        }

        // Wait for the entire window length
        Thread.sleep(1000);

        System.out.println("Time after: " + (System.currentTimeMillis() - start));
        executor.run(() -> {});
    }

    @Test
    void smoothWindow() throws InterruptedException {
        RateLimiter<Object> rateLimiter = RateLimiter.smoothBuilder(10, Duration.ofSeconds(2)).build();

        FailsafeExecutor<Object> executor = Failsafe.with(rateLimiter);

        executor.run(() -> {});

        Thread.sleep(200); // 1/10 of our 2 second window.

        executor.run(() -> {});

        // Wait less than 1/10 of our 2 second window.
        assertThrows(RateLimitExceededException.class, () -> executor.run(() -> {}));
    }
}
