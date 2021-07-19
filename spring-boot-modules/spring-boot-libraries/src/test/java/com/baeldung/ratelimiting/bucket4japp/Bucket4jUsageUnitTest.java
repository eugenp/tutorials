package com.baeldung.ratelimiting.bucket4japp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

public class Bucket4jUsageUnitTest {

    @Test
    public void givenBucketLimit_whenExceedLimit_thenConsumeReturnsFalse() {
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);
        Bucket bucket = Bucket4j.builder()
            .addLimit(limit)
            .build();

        for (int i = 1; i <= 10; i++) {
            assertTrue(bucket.tryConsume(1));
        }
        assertFalse(bucket.tryConsume(1));
    }

    @Test
    public void givenMultipletLimits_whenExceedSmallerLimit_thenConsumeReturnsFalse() {
        Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
            .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(20))))
            .build();

        for (int i = 1; i <= 5; i++) {
            assertTrue(bucket.tryConsume(1));
        }
        assertFalse(bucket.tryConsume(1));
    }

    @Test
    public void givenBucketLimit_whenThrottleRequests_thenConsumeReturnsTrue() throws InterruptedException {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(2));
        Bandwidth limit = Bandwidth.classic(1, refill);
        Bucket bucket = Bucket4j.builder()
            .addLimit(limit)
            .build();

        assertTrue(bucket.tryConsume(1));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        CountDownLatch latch = new CountDownLatch(1);

        executor.schedule(new AssertTryConsume(bucket, latch), 2, TimeUnit.SECONDS);

        latch.await();
    }

    static class AssertTryConsume implements Runnable {

        private Bucket bucket;
        private CountDownLatch latch;

        AssertTryConsume(Bucket bucket, CountDownLatch latch) {
            this.bucket = bucket;
            this.latch = latch;
        }

        @Override
        public void run() {
            assertTrue(bucket.tryConsume(1));
            latch.countDown();
        }
    }
}
