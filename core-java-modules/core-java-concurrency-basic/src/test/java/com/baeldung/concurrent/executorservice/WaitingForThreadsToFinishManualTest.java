package com.baeldung.concurrent.executorservice;

import static junit.framework.TestCase.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitingForThreadsToFinishManualTest {

    private static final Logger LOG = LoggerFactory.getLogger(WaitingForThreadsToFinishManualTest.class);
    private final static ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void givenMultipleThreads_whenUsingCountDownLatch_thenMainShoudWaitForAllToFinish() {

        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

        try {
            long startTime = System.currentTimeMillis();

            // create a CountDownLatch that waits for the 2 threads to finish
            CountDownLatch latch = new CountDownLatch(2);

            for (int i = 0; i < 2; i++) {
                WORKER_THREAD_POOL.submit(() -> {
                    try {
                        Thread.sleep(1000);
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                });
            }

            // wait for the latch to be decremented by the two threads
            latch.await();

            long processingTime = System.currentTimeMillis() - startTime;
            assertTrue(processingTime >= 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
    }

    @Test
    public void givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish() {

        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = Arrays.asList(
            new DelayedCallable("fast thread", 100),
            new DelayedCallable("slow thread", 3000));

        try {
            long startProcessingTime = System.currentTimeMillis();
            List<Future<String>> futures = WORKER_THREAD_POOL.invokeAll(callables);

            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);

            try {
                WORKER_THREAD_POOL.submit((Callable<String>) () -> {
                    Thread.sleep(1000000);
                    return null;
                });
            } catch (RejectedExecutionException ex) {
                //
            }

            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
            assertTrue(totalProcessingTime >= 3000);

            String firstThreadResponse = futures.get(0)
                .get();
            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));

            String secondThreadResponse = futures.get(1)
                .get();
            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void givenMultipleThreads_whenUsingCompletionService_thenMainThreadShouldWaitForAllToFinish() {

        CompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

        List<Callable<String>> callables = Arrays.asList(
            new DelayedCallable("fast thread", 100),
            new DelayedCallable("slow thread", 3000));

        for (Callable<String> callable : callables) {
            service.submit(callable);
        }

        try {

            long startProcessingTime = System.currentTimeMillis();

            Future<String> future = service.take();
            String firstThreadResponse = future.get();
            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));
            assertTrue(totalProcessingTime >= 100 && totalProcessingTime < 1000);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

            future = service.take();
            String secondThreadResponse = future.get();
            totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));
            assertTrue(totalProcessingTime >= 3000 && totalProcessingTime < 4000);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
        }
    }
}