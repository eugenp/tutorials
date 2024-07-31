package com.baeldung.jcache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import static org.junit.Assert.assertEquals;

public class EventListenerIntegrationTest {

    private static final String CACHE_NAME = "MyCache";
    private static final String CACHE_PROVIDER_NAME = "com.hazelcast.cache.HazelcastMemberCachingProvider";

    private Cache<String, String> cache;
    private SimpleCacheEntryListener listener;
    private MutableCacheEntryListenerConfiguration<String, String> listenerConfiguration;

    @Before
    public void setup() {
        CachingProvider cachingProvider = Caching.getCachingProvider(CACHE_PROVIDER_NAME);
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, String> config = new MutableConfiguration<String, String>();
        this.cache = cacheManager.createCache("MyCache", config);
        this.listener = new SimpleCacheEntryListener();
    }

    @After
    public void tearDown() {
        Caching.getCachingProvider(CACHE_PROVIDER_NAME).getCacheManager().destroyCache(CACHE_NAME);
    }

    @Test
    public void whenRunEvent_thenCorrect() {
        this.listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(this.listener), null, false, true);
        this.cache.registerCacheEntryListener(this.listenerConfiguration);

        assertEquals(false, this.listener.getCreated());

        this.cache.put("key", "value");
        assertEquals(true, this.listener.getCreated());
        assertEquals(false, this.listener.getUpdated());

        this.cache.put("key", "newValue");
        assertEquals(true, this.listener.getUpdated());
    }

}