package com.baeldung.park;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


class TreadMonitorsBehaviorUnitTest {

    @Test
    @Timeout(3)
    void giveThreadWhenNotifyWithoutAcquiringMonitorThrowsException() {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        // The thread was interrupted
                    }
                }
            }
        };

        assertThrows(IllegalMonitorStateException.class, () -> {
            thread.start();
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            thread.notify();
            thread.join();
        });
    }

    @Test
    @Timeout(3)
    void giveThreadWhenUnparkWithoutAcquiringMonitor() {
        final Thread thread = new Thread(LockSupport::park);
        assertTimeoutPreemptively(Duration.of(2, ChronoUnit.SECONDS), () -> {
            thread.start();
            LockSupport.unpark(thread);
        });
    }
}
