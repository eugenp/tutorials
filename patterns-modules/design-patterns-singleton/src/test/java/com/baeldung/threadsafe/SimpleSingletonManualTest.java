package com.baeldung.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleSingletonManualTest {
    @Test
    void givenUnsafeSingleton_whenAccessedConcurrently_thenMultipleInstancesCreated() throws InterruptedException {
        int threadCount = 1000;
        Set<SimpleSingleton> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                instances.add(SimpleSingleton.getInstance());
                latch.countDown();
            }).start();
        }
        latch.await();
        assertTrue(instances.size() > 1, "Multiple instances were created");
    }
}
