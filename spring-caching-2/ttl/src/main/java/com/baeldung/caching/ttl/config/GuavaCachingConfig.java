package com.baeldung.caching.ttl.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GuavaCachingConfig<T> {

    private Cache<Long, T> cache;

    Logger logger = LoggerFactory.getLogger(GuavaCachingConfig.class);

    public GuavaCachingConfig(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public T get(Long key) {
        return cache.getIfPresent(key);
    }

    public void add(Long key, T value) {
        if(key != null && value != null) {
            cache.put(key, value);
            logger.info(
                    String.format("A %s record stored in Cache with key: %s", value.getClass().getSimpleName(), key));
        }
    }
}