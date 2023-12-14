package com.baeldung.park;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.Test;

class PreemptivePermitsBehaviorUnitTest {

    private final Thread parkedThread = new Thread() {
        @Override
        public void run() {
            LockSupport.unpark(this);
            LockSupport.park();
        }
    };

    @Test
    void givenThreadWhenPreemptivePermitShouldNotPark() {
        assertTimeoutPreemptively(Duration.of(1, ChronoUnit.SECONDS), () -> {
            parkedThread.start();
            parkedThread.join();
        });
    }
}