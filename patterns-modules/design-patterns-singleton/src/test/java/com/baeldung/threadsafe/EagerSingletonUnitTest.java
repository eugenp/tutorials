package com.baeldung.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EagerSingletonUnitTest {
    @Test
    void givenEagerSingleton_whenAccessedConcurrently_thenSingleInstanceCreated()
            throws InterruptedException {

        int threadCount = 1000;
        Set<EagerSingleton> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                instances.add(EagerSingleton.getInstance());
                latch.countDown();
            }).start();
        }

        latch.await();

        assertEquals(1, instances.size(), "Only one instance should be created");
    }
}
