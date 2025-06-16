package com.baeldung.failsafe;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeException;
import dev.failsafe.Timeout;

public class TimeoutUnitTest {
    @Test
    void testTimeout() {
        Timeout<Object> timeout = Timeout.builder(Duration.ofMillis(100)).build();

        long start = System.currentTimeMillis();
        assertThrows(FailsafeException.class, () ->
            Failsafe.with(timeout)
                .get(() -> {
                    Thread.sleep(250);
                    return 2;
                })
        );
        long end = System.currentTimeMillis();
        long duration = end - start;

        assertTrue(duration >= 100); // Our timeout
        assertTrue(duration <= 300); // 250ms plus a bit.
    }

    @Test
    void testInterruption() {
        Timeout<Object> timeout = Timeout.builder(Duration.ofMillis(100))
            .withInterrupt()
            .build();

        long start = System.currentTimeMillis();
        assertThrows(FailsafeException.class, () ->
            Failsafe.with(timeout)
                .get(() -> {
                    Thread.sleep(250);
                    return 2;
                })
        );
        long end = System.currentTimeMillis();
        long duration = end - start;

        assertTrue(duration >= 100); // Our timeout
        assertTrue(duration <= 150); // Our timeout plus a bit. Notably less than our task takes.
    }
}
