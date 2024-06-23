package com.baeldung.executorservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExecutorServiceUnitTest {

    @Test
    void whenNoBlocking_thenTestFails() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        executorService.submit(r);
        assertEquals(null, r.getResult());
    }

    @Test
    void whenUsingFuture_thenTestSucceeds() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        Future<?> future = executorService.submit(r);
        future.get();
        assertEquals(2305843005992468481L, r.getResult());
    }

    @Test
    void whenShuttingDownExecutorAndWait_thenTestSucceeds() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        executorService.submit(r);
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.SECONDS);
        assertEquals(2305843005992468481L, r.getResult());
    }

    @Test
    void whenUsingThreadPoolExecutor_thenTestSucceeds() throws InterruptedException {
        MyThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExecutor(3, 6, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), 20);
        List<MyRunnable> runnables = new ArrayList<MyRunnable>();
        for (int i = 0; i < 20; i++) {
            MyRunnable r = new MyRunnable();
            runnables.add(r);
            threadPoolExecutor.submit(r);
        }
        threadPoolExecutor.waitDone();
        for (int i = 0; i < 20; i++) {
            assertEquals(2305843005992468481L, runnables.get(i).result);
        }
    }

}
