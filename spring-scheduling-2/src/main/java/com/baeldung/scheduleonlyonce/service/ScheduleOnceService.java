package com.baeldung.scheduleonlyonce.service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
public class ScheduleOnceService {

    private static final long SCHEDULED_DELAY = 1000;
    private TaskScheduler scheduler = new SimpleAsyncTaskScheduler();
    private CountDownLatch latch = new CountDownLatch(2);

    @Scheduled(initialDelay = SCHEDULED_DELAY, fixedDelay = Long.MAX_VALUE)
    public void doTaskWithIndefiniteDelay() {
        latch.countDown();
    }

    @Scheduled(initialDelay = SCHEDULED_DELAY)
    public void doTaskWithInitialDelayOnly() {
        latch.countDown();
    }

    public void schedule(Runnable task, Instant when) {
        scheduler.schedule(task, when);
    }

    public void scheduleAtIndefiniteRate(Runnable task, Instant when) {
        scheduler.scheduleAtFixedRate(task, when, Duration.ofMillis(Long.MAX_VALUE));
    }

    public void schedule(Runnable task, PeriodicTrigger trigger) {
        scheduler.schedule(task, trigger);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
