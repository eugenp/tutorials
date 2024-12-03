package com.baeldung.scheduleonlyonce;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.scheduleonlyonce.service.ScheduleOnceService;
import com.baeldung.scheduleonlyonce.trigger.OneOffTrigger;

@SpringBootTest
class ScheduleOnceServiceIntegrationTest {
    private static final long TIMEOUT = 5;

    @Autowired
    ScheduleOnceService scheduler;

    @Test
    void whenScheduledMethods_thenEachExecutesOnce() throws InterruptedException {
        boolean executed = scheduler.getLatch()
            .await(TIMEOUT, TimeUnit.SECONDS);

        assertTrue(executed);
    }

    @Test
    void whenScheduleAtInstant_thenExecutesOnce() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        scheduler.schedule(latch::countDown, aSecondFromNow());

        boolean executed = latch.await(TIMEOUT, TimeUnit.SECONDS);
        assertTrue(executed);
    }

    @Test
    void whenScheduleAtIndefiniteRate_thenExecutesOnce() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        scheduler.scheduleAtIndefiniteRate(latch::countDown, aSecondFromNow());

        boolean executed = latch.await(TIMEOUT, TimeUnit.SECONDS);
        assertTrue(executed);
    }

    @Test
    void whenScheduleWithRunOnceTrigger_thenExecutesOnce() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        scheduler.schedule(latch::countDown, new OneOffTrigger(aSecondFromNow()));

        boolean executed = latch.await(TIMEOUT, TimeUnit.SECONDS);
        assertTrue(executed);
    }

    private Instant aSecondFromNow() {
        return Instant.now()
            .plus(Duration.ofSeconds(1));
    }
}