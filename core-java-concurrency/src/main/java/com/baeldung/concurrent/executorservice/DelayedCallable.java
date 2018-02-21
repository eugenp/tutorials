package com.baeldung.concurrent.executorservice;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

public class DelayedCallable implements Callable<String> {

    private final String name;
    private final long delay;
    private final Optional<CountDownLatch> latch;

    public DelayedCallable(String name, long delay, Optional<CountDownLatch> latch) {
        this.name = name;
        this.delay = delay;
        this.latch = latch;
    }

    @Override
    public String call() {

        StopWatch worker = StopWatch.createStarted();
        do {
        } while (worker.getTime(TimeUnit.MILLISECONDS) < delay);

        latch.ifPresent(CountDownLatch::countDown);

        return name;
    }
}
