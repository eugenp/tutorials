package com.baeldung.atomicvsvolatile;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Test;

public class SafeAtomicCounterUnitTest {

    private static final int INCREMENT_COUNTER = 1000;
    private static final int TIMEOUT = 100;
    private static final int POOL_SIZE = 3;

    @Test
    public void givenMultiThread_whenSafeAtomicCounterIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
        SafeAtomicCounter safeCounter = new SafeAtomicCounter();
        IntStream.range(0, INCREMENT_COUNTER).forEach(count -> service.submit(safeCounter::increment));
        service.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        assertEquals(INCREMENT_COUNTER, safeCounter.getValue());
    }
}