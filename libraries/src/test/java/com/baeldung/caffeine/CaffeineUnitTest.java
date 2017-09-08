package com.baeldung.caffeine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineUnitTest {

    @Test
    public void given_Cache_whenPopulate_thenValueStored() {
        Cache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        String key = "A";
        DataObject dataObject = cache.getIfPresent(key);
        assertNull(dataObject);
        dataObject = cache.get(key, k -> DataObject.get("Data for A"));
        assertNotNull(dataObject);
        assertEquals(dataObject.getData(), "Data for A");
        cache.put(key, dataObject);
        dataObject = cache.getIfPresent(key);
        assertNotNull(dataObject);
        cache.invalidate(key);

        dataObject = cache.getIfPresent(key);
        assertNull(dataObject);
    }

    @Test
    public void given_LoadingCache_whenGet_thenValuePopulated() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));
        String key = "A";

        DataObject dataObject = cache.get(key);
        assertNotNull(dataObject);
        assertEquals("Data for " + key, dataObject.getData());

        Map<String, DataObject> dataObjectMap = cache.getAll(Arrays.asList("A", "B", "C"));
        assertEquals(3, dataObjectMap.size());
    }

    @Test
    public void given_AsyncLoadingCache_whenGet_thenValuePopulated() {

        AsyncLoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync(k -> DataObject.get("Data for " + k));
        String key = "A";

        cache.get(key)
                .thenAccept(dataObject -> {
            assertNotNull(dataObject);
            assertEquals("Data for " + key, dataObject.getData());
        });

        cache.getAll(Arrays.asList("A", "B", "C"))
                .thenAccept(dataObjectMap -> assertEquals(3, dataObjectMap.size()));
    }

    @Test
    public void given_LoadingCacheWithSmallSize_whenPut_thenSizeIsConstant() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));
        assertEquals(0, cache.estimatedSize());
        cache.get("A");
        assertEquals(1, cache.estimatedSize());
        cache.get("B");
        cache.cleanUp();
        assertEquals(1, cache.estimatedSize());
    }

    @Test
    public void given_LoadingCacheWithWeighter_whenPut_thenSizeIsConstant() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumWeight(10)
                .weigher((k,v) -> 5)
                .build(k -> DataObject.get("Data for " + k));
        assertEquals(0, cache.estimatedSize());
        cache.get("A");
        assertEquals(1, cache.estimatedSize());
        cache.get("B");
        assertEquals(2, cache.estimatedSize());
        cache.get("C");
        cache.cleanUp();
        assertEquals(2, cache.estimatedSize());
    }

    @Test
    public void given_TimeEvictionCache_whenTimeLeft_thenValueEvicted() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));

        cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .weakKeys()
                .weakValues()
                .softValues()
                .build(k -> DataObject.get("Data for " + k));

        cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, DataObject>() {
                    @Override
                    public long expireAfterCreate(@Nonnull String key, @Nonnull DataObject value, long currentTime) {
                        return value.getData().length()*1000;
                    }

                    @Override
                    public long expireAfterUpdate(@Nonnull String key, @Nonnull DataObject value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(@Nonnull String key, @Nonnull DataObject value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                })
                .build(k -> DataObject.get("Data for " + k));

        cache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));
    }

    @Test
    public void givenCache_whenStatsEnabled_thenStatsRecorded() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .recordStats()
                .build(k -> DataObject.get("Data for " + k));
        cache.get("A");
        cache.get("A");
        assertEquals(1, cache.stats().hitCount());
        assertEquals(1, cache.stats().missCount());
    }
}
