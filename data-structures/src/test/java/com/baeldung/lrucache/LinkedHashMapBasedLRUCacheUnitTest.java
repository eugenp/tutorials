package com.baeldung.lrucache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.Test;

public class LinkedHashMapBasedLRUCacheUnitTest {

    @Test
    public void whenAddDataToTheCache_ThenLeastRecentlyDataWillEvict() {
        LinkedHashMapBasedLRUCache<String, String> lruCache = new LinkedHashMapBasedLRUCache<>(3);
        lruCache.put("1", "test1");
        lruCache.put("2", "test2");
        lruCache.put("3", "test3");
        lruCache.put("4", "test4");

        assertEquals(3, lruCache.size());

        assertFalse(lruCache.containsKey("1"));

        assertEquals("test2", lruCache.get("2"));
        assertEquals("test3", lruCache.get("3"));
        assertEquals("test4", lruCache.get("4"));
    }

    @Test
    public void whenPutDataInConcurrentToCache_ThenNoDataLost() throws Exception {
        final int size = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(50);
        Map<Integer, String> cache = Collections.synchronizedMap(new LinkedHashMapBasedLRUCache<>(size));
        CountDownLatch countDownLatch = new CountDownLatch(size);
        try {
            IntStream.range(0, size)
                .<Runnable> mapToObj(key -> () -> {
                    cache.put(key, "value" + key);
                    countDownLatch.countDown();
                })
                .forEach(executorService::submit);
            countDownLatch.await();
        } finally {
            executorService.shutdown();
        }
        assertEquals(size, cache.size());
        IntStream.range(0, size)
            .forEach(i -> assertEquals("value" + i, cache.get(i)));
    }
}