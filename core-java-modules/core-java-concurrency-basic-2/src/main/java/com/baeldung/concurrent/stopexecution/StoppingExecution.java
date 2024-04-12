package com.baeldung.concurrent.stopexecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StoppingExecution {

    private static final Logger LOG = LoggerFactory.getLogger(StoppingExecution.class);

    public static void main(String[] args) {
        StoppingExecution.testUsingLoop();
        StoppingExecution.testUsingTimer();
        StoppingExecution.testUsingFuture();
        StoppingExecution.testScheduledExecutor();
        StoppingExecution.testSteppedProcess();
    }

    public static void testUsingLoop() {
        LOG.info("using loop started");
        long start = System.currentTimeMillis();
        long end = start + 30 * 1000; // 30 seconds
        while (System.currentTimeMillis() < end) {
            LOG.info("running task");
            new FixedTimeTask(7 * 1000).run(); // 7 seconds
        }
        LOG.info("using loop ended");
    }

    public static void testUsingTimer() {
        LOG.info("using timer started");
        Thread thread = new Thread(new LongRunningTask());
        thread.start();

        Timer timer = new Timer();
        TimeOutTask timeOutTask = new TimeOutTask(thread, timer);

        LOG.info("scheduling timeout in 3 seconds");
        timer.schedule(timeOutTask, 3000);
        LOG.info("using timer ended");
    }

    public static void testUsingFuture() {
        LOG.info("using future started");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new LongRunningTask());
        try {
            LOG.info("future get with 7 seconds timeout");
            future.get(7, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            LOG.info("future timeout");
            future.cancel(true);
        } catch (Exception e) {
            LOG.info("future exception", e);
        } finally {
            executor.shutdownNow();
        }
        LOG.info("using future ended");
    }

    public static void testScheduledExecutor() {
        LOG.info("using future schedule executor started");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Future future = executor.submit(new LongRunningTask());
        Runnable cancelTask = () -> future.cancel(true);

        LOG.info("cancel task in 3 seconds");
        executor.schedule(cancelTask, 3000, TimeUnit.MILLISECONDS);
        executor.shutdown();
        LOG.info("using future schedule executor ended");
    }

    public static void testSteppedProcess() {
        List<Step> steps = Stream.of(new Step(1), new Step(2), new Step(3), new Step(4)).collect(Collectors.toList());

        LOG.info("stepped process started");
        Thread thread = new Thread(new SteppedTask(steps));
        thread.start();

        Timer timer = new Timer();
        TimeOutTask timeOutTask = new TimeOutTask(thread, timer);
        timer.schedule(timeOutTask, 10000);
    }
}
