package com.baeldung.concurrent.executorservice;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitingForThreadsToFinishTest {

    private static final Logger LOG = LoggerFactory.getLogger(WaitingForThreadsToFinishTest.class);
    private final static ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

    @Test
    public void givenMultipleThreads_whenUsingCountDownLatch_thenMainShoudWaitForAllToFinish() {
        long delay = 100;
        int threads = 2;
        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(1);

        try {
            StopWatch watch = StopWatch.createStarted();

            // create a CountDownLatch that waits for the 2 threads to finish
            CountDownLatch latch = new CountDownLatch(threads);

            for (int i = 0; i < threads; i++) {
                WORKER_THREAD_POOL.submit(new DelayedCallable("thread" + i, delay, Optional.of(latch)));
            }

            // wait for the latch to be decremented by the two threads
            latch.await();

            watch.stop();
            long processingTime = watch.getTime(TimeUnit.MILLISECONDS);
            assertThat(processingTime).isGreaterThanOrEqualTo(delay * threads);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
    }

    @Test
    public void givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish() {

        long fastThread = 10;
        long slowThread = 300;
        long verySlowThread = 100000;
        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(2);

        List<Callable<String>> callables = Arrays.asList(new DelayedCallable("fast thread", fastThread, Optional.empty()), new DelayedCallable("slow thread", slowThread, Optional.empty()));

        try {
            StopWatch watch = StopWatch.createStarted();
            List<Future<String>> futures = WORKER_THREAD_POOL.invokeAll(callables);

            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);

            try {
                WORKER_THREAD_POOL.submit((Callable<String>) () -> {
                    Thread.sleep(verySlowThread);
                    return null;
                });
            } catch (RejectedExecutionException ex) {
                //
            }

            watch.stop();
            long totalProcessingTime = watch.getTime(TimeUnit.MILLISECONDS);
            assertThat(totalProcessingTime).isBetween(slowThread, verySlowThread);

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
        long fastThread = 10;
        long slowThread = 300;
        CompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

        List<Callable<String>> callables = Arrays.asList(new DelayedCallable("fast thread", fastThread, Optional.empty()), new DelayedCallable("slow thread", slowThread, Optional.empty()));

        for (Callable<String> callable : callables) {
            service.submit(callable);
        }

        try {

            StopWatch watch = StopWatch.createStarted();

            Future<String> future = service.take();
            String firstThreadResponse = future.get();
            long totalProcessingTime = watch.getTime(TimeUnit.MILLISECONDS);

            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));
            assertThat(totalProcessingTime).isBetween(fastThread, slowThread);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

            future = service.take();
            String secondThreadResponse = future.get();
            totalProcessingTime = watch.getTime(TimeUnit.MILLISECONDS);

            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));
            assertThat(totalProcessingTime).isGreaterThanOrEqualTo(slowThread);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
        }
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread()
                .interrupt();
        }
    }
}
