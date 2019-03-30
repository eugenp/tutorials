package com.baeldung.concurrent.atomic;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * This test shows the behaviour of a thread-unsafe class in a multithreaded scenario. We are calling
 * the increment methods 1000 times from a pool of 3 threads. In most of the cases, the counter will 
 * less than 1000, because of lost updates, however, occasionally it may reach 1000, when no threads
 * called the method simultaneously. This may cause the build to fail occasionally. Hence excluding this
 * test from build by adding this in manual test
 */
public class ThreadUnsafeCounterManualTest {

    @Test
    public void givenMultiThread_whenUnsafeCounterIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        UnsafeCounter unsafeCounter = new UnsafeCounter();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(unsafeCounter::increment));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, unsafeCounter.getValue());
    }
    
}
