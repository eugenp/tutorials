package com.baeldung.jcache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import static org.junit.Assert.assertEquals;

public class EntryProcessorIntegrationTest {

    private static final String CACHE_NAME = "MyCache";
    private static final String CACHE_PROVIDER_NAME = "com.hazelcast.cache.HazelcastMemberCachingProvider";

    private Cache<String, String> cache;

    @Before
    public void instantiateCache() {
        CachingProvider cachingProvider = Caching.getCachingProvider(CACHE_PROVIDER_NAME);
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, String> config = new MutableConfiguration<>();
        this.cache = cacheManager.createCache(CACHE_NAME, config);
        this.cache.put("key", "value");
    }

    @After
    public void tearDown() {
        Caching.getCachingProvider(CACHE_PROVIDER_NAME).getCacheManager().destroyCache(CACHE_NAME);
    }

    @Test
    public void whenModifyValue_thenCorrect() {
        this.cache.invoke("key", new SimpleEntryProcessor());
        assertEquals("value - modified", cache.get("key"));
    }
}