package com.baeldung.caching.twolevelcache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.data.redis.cache.RedisCache;

public class CustomerCacheInterceptor extends CacheInterceptor {

    private final CacheManager caffeineCacheManager;

    public CustomerCacheInterceptor(CacheManager caffeineCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
    }

    @Override
    protected Cache.ValueWrapper doGet(Cache cache, Object key) {
        Cache.ValueWrapper existingCacheValue = super.doGet(cache, key);

        if (existingCacheValue != null && cache.getClass() == RedisCache.class) {
            Cache caffeineCache = caffeineCacheManager.getCache(cache.getName());
            if (caffeineCache != null) {
                caffeineCache.putIfAbsent(key, existingCacheValue.get());
            }
        }

        return existingCacheValue;
    }
}
