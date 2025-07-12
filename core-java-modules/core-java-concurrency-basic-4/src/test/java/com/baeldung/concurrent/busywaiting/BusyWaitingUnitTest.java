package com.baeldung.concurrent.busywaiting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusyWaitingUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(BusyWaitingUnitTest.class);

    @Test
    void givenWorkerThread_whenBusyWaiting_thenAssertExecutedMultipleTimes() {
        AtomicBoolean taskDone = new AtomicBoolean(false);
        long counter = 0;

        Thread worker = new Thread(() -> {
            simulateThreadWork();
            taskDone.set(true);
        });

        worker.start();

        while (!taskDone.get()) {
            counter++;
        }

        logger.info("Counter: {}", counter);
        assertTrue(counter != 1);
    }

    @Test
    void givenWorkerThread_whenUsingWaitNotify_thenWaitEfficientlyOnce() {
        AtomicBoolean taskDone = new AtomicBoolean(false);
        final Object lock = new Object();
        long counter = 0;

        Thread worker = new Thread(() -> {
            simulateThreadWork();
            synchronized (lock) {
                taskDone.set(true);
                lock.notify();
            }
        });

        worker.start();

        synchronized (lock) {
            while (!taskDone.get()) {
                counter++;
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread()
                        .interrupt();
                    fail("Test case failed due to thread interruption!");
                }
            }
        }

        assertEquals(1, counter);
    }

    private void simulateThreadWork() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }
    }

}
