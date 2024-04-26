package com.baeldung.executorservice.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExecutorServiceUnitTest {

    @Test
    void whenNoWaitMechanism_thenResultIsNull() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        executorService.submit(r);
        assertNull(r.getResult());
    }

    @Test
    void whenWeUseFuture_thenCurrentThreadsWaits() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        Future<?> future = executorService.submit(r);
        future.get();
        assertEquals(2305843005992468481L, r.getResult());
    }

    @Test
    void whenWeShutExecutorService_thenCurrentThreadsWaits() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        executorService.submit(r);
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.SECONDS);
        assertEquals(2305843005992468481L, r.getResult());
    }

    @Test
    void givenWeSubmitManyRunnables_whenWeShutExecutorService_thenCurrentThreadsWaits() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        MyRunnable r1 = new MyRunnable();
        MyRunnable r2 = new MyRunnable();
        executorService.submit(r1);
        executorService.submit(r2);
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.SECONDS);
        assertEquals(2305843005992468481L, r1.getResult());
        assertEquals(2305843005992468481L, r2.getResult());
    }

}
