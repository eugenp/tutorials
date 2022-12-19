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

    private Map<Integer, Integer> frequencyMap;

    @BeforeEach
    public void setup() {
        frequencyMap = new ConcurrentHashMap<>();
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
            return frequencyMap.get(k) + 1;
        });

        Callable<Integer> readNow = () -> frequencyMap.get(1);
        Callable<Integer> readAfter1001Ms = () -> {
            TimeUnit.MILLISECONDS.sleep(1001);
            return frequencyMap.get(1);
        };

        threadExecutor.submit(writeAfter1Sec);
        List<Future<Integer>> results = threadExecutor.invokeAll(asList(readNow, readAfter1001Ms));

        assertEquals(0, results.get(0).get());
        assertEquals(1, results.get(1).get());

        if (threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
            threadExecutor.shutdown();
        }
    }

    @Test
    public void givenOneThreadIsWriting_whenAnotherThreadWritesAtSameKey_thenWaitAndGetCorrectValue() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);

        Callable<Integer> writeAfter5Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(5);
            return frequencyMap.get(k) + 1;
        });

        Callable<Integer> writeAfter1Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(1);
            return frequencyMap.get(k) + 1;
        });

        List<Future<Integer>> results = threadExecutor.invokeAll(asList(writeAfter5Sec, writeAfter1Sec));

        assertEquals(1, results.get(0).get());
        assertEquals(2, results.get(1).get());

        if (threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
            threadExecutor.shutdown();
        }
    }

    @Test
    public void givenOneThreadIsWriting_whenAnotherThreadWritesAtDifferentKey_thenNotWaitAndGetCorrectValue() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);

        Callable<Integer> writeAfter5Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(5);
            return frequencyMap.get(k) + 1;
        });

        AtomicLong time = new AtomicLong(System.currentTimeMillis());
        Callable<Integer> writeAfter1Sec = () -> frequencyMap.computeIfPresent(2, (k, v) -> {
            sleep(1);
            time.set((System.currentTimeMillis() - time.get()) / 1000);
            return frequencyMap.get(k) + 1;
        });

        threadExecutor.invokeAll(asList(writeAfter5Sec, writeAfter1Sec));

        assertEquals(1, time.get());

        if (threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
            threadExecutor.shutdown();
        }
    }

    private static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}