package com.baeldung.threadsafe;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumSingletonUnitTest {
    @Test
    void givenEnumSingleton_whenAccessedConcurrently_thenSingleInstanceCreated()
            throws InterruptedException {

        Set<EnumSingleton> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                instances.add(EnumSingleton.INSTANCE);
                latch.countDown();
            }).start();
        }

        latch.await();
        assertEquals(1, instances.size());
    }
}
