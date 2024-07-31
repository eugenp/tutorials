package com.baeldung.park;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class ThreadInterruptedBehaviorUnitTest {

    @Test
    @Timeout(3)
    void givenParkedThreadWhenInterruptedShouldNotResetInterruptedFlag() throws InterruptedException {
        final Thread thread = new Thread(LockSupport::park);
        thread.start();
        thread.interrupt();
        assertTrue(thread.isInterrupted(), "The thread should have the interrupted flag");
        thread.join();
    }

    @Test
    @Timeout(3)
    void givenParkedThreadWhenNotInterruptedShouldNotHaveInterruptedFlag() throws InterruptedException {
        final Thread thread = new Thread(LockSupport::park);
        thread.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        LockSupport.unpark(thread);
        assertFalse(thread.isInterrupted(), "The thread shouldn't have the interrupted flag");
        thread.join();
    }

    @Test
    @Timeout(3)
    void givenWaitingThreadWhenNotInterruptedShouldNotHaveInterruptedFlag() throws InterruptedException {

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

        thread.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        thread.interrupt();
        thread.join();
        assertFalse(thread.isInterrupted(), "The thread shouldn't have the interrupted flag");
    }
}