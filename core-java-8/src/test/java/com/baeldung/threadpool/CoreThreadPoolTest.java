package com.baeldung.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoreThreadPoolTest {

    @Test(timeout = 1000)
    public void whenCallingExecuteWithRunnable_thenRunnableIsExecuted() throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(1);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            System.out.println("Hello World");
            lock.countDown();
        });

        lock.await(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void whenUsingExecutorServiceAndFuture_thenCanWaitOnFutureResult() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> "Hello World");
        String result = future.get();

        assertEquals("Hello World", result);

    }

    @Test
    public void whenUsingFixedThreadPool_thenCoreAndMaximumThreadSizeAreTheSame() {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        assertEquals(2, executor.getPoolSize());
        assertEquals(1, executor.getQueue().size());

    }

    @Test
    public void whenUsingCachedThreadPool_thenPoolSizeGrowsUnbounded() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        assertEquals(3, executor.getPoolSize());
        assertEquals(0, executor.getQueue().size());

    }

    @Test(timeout = 1000)
    public void whenUsingSingleThreadPool_thenTasksExecuteSequentially() throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(2);
        AtomicInteger counter = new AtomicInteger();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            counter.set(1);
            lock.countDown();
        });
        executor.submit(() -> {
            counter.compareAndSet(1, 2);
            lock.countDown();
        });

        lock.await(1000, TimeUnit.MILLISECONDS);
        assertEquals(2, counter.get());

    }

    @Test(timeout = 1000)
    public void whenSchedulingTask_thenTaskExecutesWithinGivenPeriod() throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(1);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.schedule(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, TimeUnit.MILLISECONDS);

        lock.await(1000, TimeUnit.MILLISECONDS);

    }

    @Test(timeout = 1000)
    public void whenSchedulingTaskWithFixedPeriod_thenTaskExecutesMultipleTimes() throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        lock.await();
        future.cancel(true);

    }

    @Test
    public void whenUsingForkJoinPool_thenSumOfTreeElementsIsCalculatedCorrectly() {

        TreeNode tree = new TreeNode(5,
                new TreeNode(3), new TreeNode(2,
                new TreeNode(2), new TreeNode(8)));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int sum = forkJoinPool.invoke(new CountingTask(tree));

        assertEquals(20, sum);
    }


}
