package com.baeldung.java.concurrentmap;

import org.junit.Test;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentNavigableMapManualTests {

    @Test
    public void givenSkipListMap_whenAccessInMultiThreads_thenOrderingStable() throws InterruptedException {
        NavigableMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();

        updateMapConcurrently(skipListMap, 4);

        Iterator<Integer> skipListIter = skipListMap.keySet().iterator();
        int previous = skipListIter.next();
        while (skipListIter.hasNext()) {
            int current = skipListIter.next();
            assertTrue(previous < current);
        }
    }

    private void updateMapConcurrently(NavigableMap<Integer, String> navigableMap, int concurrencyLevel) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(concurrencyLevel);
        for (int i = 0; i < concurrencyLevel; i++) {
            executorService.execute(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int j = 0; j < 10000; j++) {
                    navigableMap.put(random.nextInt(), "test");
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void givenSkipListMap_whenNavConcurrently_thenCountCorrect() throws InterruptedException {
        NavigableMap<Integer, Integer> skipListMap = new ConcurrentSkipListMap<>();
        int count = countMapElementByPollingFirstEntry(skipListMap, 10000, 4);
        assertEquals(10000 * 4, count);
    }

    @Test
    public void givenTreeMap_whenNavConcurrently_thenCountError() throws InterruptedException {
        NavigableMap<Integer, Integer> treeMap = new TreeMap<>();
        int count = countMapElementByPollingFirstEntry(treeMap, 10000, 4);
        assertNotEquals(10000 * 4, count);
    }

    private int countMapElementByPollingFirstEntry(NavigableMap<Integer, Integer> navigableMap, int elementCount, int concurrencyLevel) throws InterruptedException {
        for (int i = 0; i < elementCount * concurrencyLevel; i++) {
            navigableMap.put(i, i);
        }
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(concurrencyLevel);
        for (int j = 0; j < concurrencyLevel; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < elementCount; i++) {
                    if (navigableMap.pollFirstEntry() != null) {
                        counter.incrementAndGet();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return counter.get();
    }

}
