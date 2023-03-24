package com.baeldung.timebaseduuid;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDCreatorBenchmark {

    public static void main(String[] args) throws InterruptedException {

        int threadCount = 128;
        int iterationCount = 100_000;
        Map<UUID, Long> uuidMap = new ConcurrentHashMap<>();
        AtomicLong collisionCount = new AtomicLong();
        long startNanos = System.nanoTime();
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        for (long i = 0; i < threadCount; i++) {
            long threadId = i;
            new Thread(() -> {
                for (long j = 0; j < iterationCount; j++) {
                    UUID uuid = UuidCreator.getTimeBased();
                    Long existingUUID = uuidMap.put(uuid, (threadId * iterationCount) + j);
                    if(existingUUID != null) {
                        collisionCount.incrementAndGet();
                    }
                }
                endLatch.countDown();
            }).start();
        }

        endLatch.await();
        System.out.println(threadCount * iterationCount + " UUIDs generated, " + collisionCount + " collisions in "
                + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos) + "ms");
    }
}
