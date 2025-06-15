package com.baeldung.failsafe;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import dev.failsafe.Bulkhead;
import dev.failsafe.BulkheadFullException;
import dev.failsafe.Failsafe;

public class BulkheadUnitTest {
    @Test
    void rejectExcessCalls() throws InterruptedException {
        Bulkhead<Object> bulkhead = Bulkhead.builder(1).build();

        Thread t = new Thread(() -> {
            Failsafe.with(bulkhead).run(() -> Thread.sleep(500));
        });
        t.start();

        // Ensure the thread has started.
        Thread.sleep(100);

        assertThrows(BulkheadFullException.class, () ->
            Failsafe.with(bulkhead).run(() -> {})
        );

        t.join();
    }

    @Test
    void waitForCapacity() throws InterruptedException {
        Bulkhead<Object> bulkhead = Bulkhead.builder(1)
            .withMaxWaitTime(Duration.ofMillis(1000))
            .build();

        Thread t = new Thread(() -> {
            Failsafe.with(bulkhead).run(() -> Thread.sleep(500));
        });
        t.start();

        // Ensure the thread has started.
        Thread.sleep(100);

        long start = System.currentTimeMillis();
        Integer result = Failsafe.with(bulkhead).get(() -> 1);
        long end = System.currentTimeMillis();
        long duration = end - start;

        assertEquals(1, result);

        assertTrue(duration >= 400); // Our action time minus our startup pause.
        assertTrue(duration <= 550); // Our action time plus a bit. Notably less than our timeout.


        t.join();
    }
}
