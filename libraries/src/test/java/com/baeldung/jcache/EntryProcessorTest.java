package com.baeldung.jcache;

import static org.junit.Assert.assertEquals;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.junit.Before;
import org.junit.Test;

public class EntryProcessorTest {

    private Cache<String, String> cache;

    @Before
    public void instantiateCache() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, String> config = new MutableConfiguration<String, String>();
        this.cache = cacheManager.createCache("MyCache", config);
        this.cache.put("key", "value");
    }

    @Test
    public void whenModifyValue_thenCorrect() {
        this.cache.invoke("key", new SimpleEntryProcessor());
        assertEquals("value - modified", cache.get("key"));
    }
}
