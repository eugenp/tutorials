package com.baeldung.park;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.Test;

class RepeatedPreemptivePermitsBehaviorUnitTest {

    private final Thread parkedThread = new Thread() {
        @Override
        public void run() {
            LockSupport.unpark(this);
            LockSupport.unpark(this);
            LockSupport.park();
            LockSupport.park();
        }
    };

    @Test
    void givenThreadWhenRepeatedPreemptivePermitShouldPark() {
        Callable<Boolean> callable = () -> {
            parkedThread.start();
            parkedThread.join();
            return true;
        };

        boolean result = false;
        final Future<Boolean> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            result = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // Expected the thread to be parked
        }
        assertFalse(result, "The thread should be parked");
    }
}