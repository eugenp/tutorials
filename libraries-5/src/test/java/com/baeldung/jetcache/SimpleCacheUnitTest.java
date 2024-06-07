package com.baeldung.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheResultCode;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimpleCacheUnitTest {
    @Test
    void testSimpleLinkedHashMapCache() {
        Cache<Integer, String> cache = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder()
            .limit(100)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .buildCache();

        cache.put(1, "Hello");
        assertEquals("Hello", cache.get(1));

        cache.remove(1);
        assertNull(cache.get(1));
    }

    @Test
    void testSimpleCaffeineCache() {
        Cache<Integer, String> cache = CaffeineCacheBuilder.createCaffeineCacheBuilder()
            .limit(100)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .buildCache();

        cache.put(1, "Hello");
        assertEquals("Hello", cache.get(1));

        cache.remove(1);
        assertNull(cache.get(1));
    }

    @Test
    void testMissingCacheValues() throws InterruptedException {
        Cache<Integer, String> cache = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder()
            .limit(5)
            .expireAfterWrite(10, TimeUnit.MILLISECONDS)
            .buildCache();

        cache.put(1, "Hello");

        TimeUnit.MILLISECONDS.sleep(100);

        // This was expired.
        assertEquals(CacheResultCode.EXPIRED, cache.GET(1).getResultCode());

        // This was never present.
        assertEquals(CacheResultCode.NOT_EXISTS, cache.GET(2).getResultCode());
    }

    @Test
    void testBulk() {
        Cache<Integer, String> cache = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder()
            .limit(5)
            .buildCache();

        Map<Integer, String> putMap = new HashMap<>();
        putMap.put(1, "One");
        putMap.put(2, "Two");
        putMap.put(3, "Three");
        cache.putAll(putMap);

        Map<Integer, String> values = cache.getAll(new HashSet<>(Arrays.asList(1, 2, 3, 4)));
        assertEquals("One", values.get(1));
        assertEquals("Two", values.get(2));
        assertEquals("Three", values.get(3));
        assertNull(values.get(4));
    }
}
