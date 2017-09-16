package com.baeldung.jcache;

import static org.junit.Assert.assertEquals;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CacheLoaderTest {

    private Cache<Integer, String> cache;

    @Before
    public void setup() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<Integer, String> config = new MutableConfiguration<Integer, String>().setReadThrough(true)
            .setCacheLoaderFactory(new FactoryBuilder.SingletonFactory<>(new SimpleCacheLoader()));
        this.cache = cacheManager.createCache("SimpleCache", config);
    }

    @Test
    public void whenReadingFromStorage_thenCorrect() {
        for (int i = 1; i < 4; i++) {
            String value = cache.get(i);
            assertEquals("fromCache" + i, value);
        }
    }
    
    @After
    public void closeCache() {
        cache.close();
    }
}
