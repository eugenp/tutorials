package com.baeldung.threadsafe;

import com.baledung.billpugh.BillPughSingleton;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.*;
import java.util.Set;
import java.util.HashSet;

public class BillPughSingletonUnitTest {
    @Test
    void testThreadSafety() throws InterruptedException {
        int numberOfThreads = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        Set<BillPughSingleton> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                instances.add(BillPughSingleton.getInstance());
                latch.countDown();
            }).start();
        }

        latch.await(5, TimeUnit.SECONDS);

        assertEquals(1, instances.size(), "All threads should get the same instance");
    }
}