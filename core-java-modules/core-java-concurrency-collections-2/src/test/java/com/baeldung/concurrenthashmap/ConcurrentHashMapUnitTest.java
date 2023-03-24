package com.baeldung.concurrenthashmap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrentHashMapUnitTest {

    private Map<Integer, Integer> frequencyMap = new ConcurrentHashMap<>();

    @BeforeEach
    public void setup() {
        frequencyMap.put(0, 0);
        frequencyMap.put(1, 0);
        frequencyMap.put(2, 0);
    }

    @AfterEach
    public void teardown() {
        frequencyMap.clear();
    }

    @Test
    public void givenOneThreadIsWriting_whenAnotherThreadReads_thenGetCorrectValue() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3);

        Runnable writeAfter1Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(1);
            return v + 1;
        });

        Callable<Integer> readNow = () -> frequencyMap.get(1);
        Callable<Integer> readAfter2sec = () -> {
            sleep(2);
            return frequencyMap.get(1);
        };

        threadExecutor.submit(writeAfter1Sec);
        List<Future<Integer>> results = threadExecutor.invokeAll(asList(readNow, readAfter2sec));

        assertEquals(0, results.get(0).get());
        assertEquals(1, results.get(1).get());

        threadExecutor.shutdown();
    }

    @Test
    public void givenOneThreadIsWriting_whenAnotherThreadWritesAtSameKey_thenWaitAndGetCorrectValue() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);

        Callable<Integer> writeAfter5Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(5);
            return v + 1;
        });

        Callable<Integer> writeAfter1Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(1);
            return v + 1;
        });

        Future<Integer> result1 = threadExecutor.submit(writeAfter5Sec);
        sleep(1);
        Future<Integer> result2 = threadExecutor.submit(writeAfter1Sec);

        assertEquals(1, result1.get());
        assertEquals(2, result2.get());

        threadExecutor.shutdown();
    }

    @Test
    public void givenOneThreadIsWriting_whenAnotherThreadWritesAtDifferentKey_thenNotWait() throws InterruptedException {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);

        Callable<Integer> writeAfter5Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(5);
            return v + 1;
        });

        AtomicLong time = new AtomicLong(System.currentTimeMillis());
        Callable<Integer> writeAfter1Sec = () -> frequencyMap.computeIfPresent(2, (k, v) -> {
            sleep(1);
            time.set((System.currentTimeMillis() - time.get()) / 1000);
            return v + 1;
        });

        threadExecutor.invokeAll(asList(writeAfter5Sec, writeAfter1Sec));

        assertEquals(1, time.get());

        threadExecutor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
