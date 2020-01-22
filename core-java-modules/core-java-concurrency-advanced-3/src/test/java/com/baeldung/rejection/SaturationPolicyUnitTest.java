package com.baeldung.rejection;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SaturationPolicyUnitTest {

    private ThreadPoolExecutor executor;

    @After
    public void shutdownExecutor() {
        if (executor != null && !executor.isTerminated()) {
            executor.shutdownNow();
        }
    }

    @Ignore
    @Test
    public void givenAbortPolicy_WhenSaturated_ThenShouldThrowRejectedExecutionException() {
        executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new SynchronousQueue<>(), new AbortPolicy());
        executor.execute(() -> waitFor(100));

        assertThatThrownBy(() -> executor.execute(() -> System.out.println("Will be rejected"))).isInstanceOf(RejectedExecutionException.class);
    }

    @Ignore
    @Test
    public void givenCallerRunsPolicy_WhenSaturated_ThenTheCallerThreadRunsTheTask() {
        executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new SynchronousQueue<>(), new CallerRunsPolicy());
        executor.execute(() -> waitFor(100));

        long startTime = System.nanoTime();
        executor.execute(() -> waitFor(100));
        double blockedDuration = (System.nanoTime() - startTime) / 1_000_000.0;

        assertThat(blockedDuration).isGreaterThanOrEqualTo(100);
    }

    @Test
    public void givenDiscardPolicy_WhenSaturated_ThenExecutorDiscardsTheNewTask() throws InterruptedException {
        executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new SynchronousQueue<>(), new DiscardPolicy());
        executor.execute(() -> waitFor(100));

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        executor.execute(() -> queue.offer("Result"));

        assertThat(queue.poll(200, MILLISECONDS)).isNull();
    }

    @Test
    public void givenDiscardOldestPolicy_WhenSaturated_ThenExecutorDiscardsTheOldestTask() {
        executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new ArrayBlockingQueue<>(2), new DiscardOldestPolicy());
        executor.execute(() -> waitFor(100));

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        executor.execute(() -> queue.offer("First"));
        executor.execute(() -> queue.offer("Second"));
        executor.execute(() -> queue.offer("Third"));

        waitFor(150);
        List<String> results = new ArrayList<>();
        queue.drainTo(results);
        assertThat(results).containsExactlyInAnyOrder("Second", "Third");
    }

    @Test
    public void givenGrowPolicy_WhenSaturated_ThenExecutorIncreaseTheMaxPoolSize() {
        executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new ArrayBlockingQueue<>(2), new GrowPolicy());
        executor.execute(() -> waitFor(100));

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        executor.execute(() -> queue.offer("First"));
        executor.execute(() -> queue.offer("Second"));
        executor.execute(() -> queue.offer("Third"));

        waitFor(150);
        List<String> results = new ArrayList<>();
        queue.drainTo(results);
        assertThat(results).containsExactlyInAnyOrder("First", "Second", "Third");
    }

    @Test
    public void givenExecutorIsTerminated_WhenSubmittedNewTask_ThenTheSaturationPolicyApplies() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new LinkedBlockingQueue<>());
        executor.shutdownNow();

        assertThatThrownBy(() -> executor.execute(() -> {
        })).isInstanceOf(RejectedExecutionException.class);
    }

    @Test
    public void givenExecutorIsTerminating_WhenSubmittedNewTask_ThenTheSaturationPolicyApplies() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, MILLISECONDS, new LinkedBlockingQueue<>());
        executor.execute(() -> waitFor(100));
        executor.shutdown();

        assertThatThrownBy(() -> executor.execute(() -> {
        })).isInstanceOf(RejectedExecutionException.class);
    }

    private static class GrowPolicy implements RejectedExecutionHandler {

        private final Lock lock = new ReentrantLock();

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            lock.lock();
            try {
                executor.setMaximumPoolSize(executor.getMaximumPoolSize() + 1);
            } finally {
                lock.unlock();
            }

            executor.submit(r);
        }
    }

    private void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
