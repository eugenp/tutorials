package com.baeldung.infinispan;

import com.baeldung.infinispan.listener.CacheListener;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionType;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;

import java.util.concurrent.TimeUnit;

public class CacheConfiguration {

    public static final String SIMPLE_HELLO_WORLD_CACHE = "simple-hello-world-cache";
    public static final String EXPIRING_HELLO_WORLD_CACHE = "expiring-hello-world-cache";
    public static final String EVICTING_HELLO_WORLD_CACHE = "evicting-hello-world-cache";
    public static final String PASSIVATING_HELLO_WORLD_CACHE = "passivating-hello-world-cache";
    public static final String TRANSACTIONAL_CACHE = "transactional-cache";

    public DefaultCacheManager cacheManager() {
        return new DefaultCacheManager();
    }

    public Cache<String, Integer> transactionalCache(DefaultCacheManager cacheManager, CacheListener listener) {
        return this.buildCache(TRANSACTIONAL_CACHE, cacheManager, listener, transactionalConfiguration());
    }

    public Cache<String, String> simpleHelloWorldCache(DefaultCacheManager cacheManager, CacheListener listener) {
        return this.buildCache(SIMPLE_HELLO_WORLD_CACHE, cacheManager, listener, new ConfigurationBuilder().build());
    }

    public Cache<String, String> expiringHelloWorldCache(DefaultCacheManager cacheManager, CacheListener listener) {
        return this.buildCache(EXPIRING_HELLO_WORLD_CACHE, cacheManager, listener, expiringConfiguration());
    }

    public Cache<String, String> evictingHelloWorldCache(DefaultCacheManager cacheManager, CacheListener listener) {
        return this.buildCache(EVICTING_HELLO_WORLD_CACHE, cacheManager, listener, evictingConfiguration());
    }

    public Cache<String, String> passivatingHelloWorldCache(DefaultCacheManager cacheManager, CacheListener listener) {
        return this.buildCache(PASSIVATING_HELLO_WORLD_CACHE, cacheManager, listener, passivatingConfiguration());
    }

    private <K, V> Cache<K, V> buildCache(String cacheName, DefaultCacheManager cacheManager, CacheListener listener, Configuration configuration) {

        cacheManager.defineConfiguration(cacheName, configuration);
        Cache<K, V> cache = cacheManager.getCache(cacheName);
        cache.addListener(listener);
        return cache;
    }

    private Configuration expiringConfiguration() {
        return new ConfigurationBuilder().expiration().lifespan(1, TimeUnit.SECONDS).build();
    }

    private Configuration evictingConfiguration() {
        return new ConfigurationBuilder().memory().evictionType(EvictionType.COUNT).size(1).build();
    }

    private Configuration passivatingConfiguration() {
        return new ConfigurationBuilder().memory().evictionType(EvictionType.COUNT).size(1).persistence().passivation(true).addSingleFileStore().purgeOnStartup(true).location(System.getProperty("java.io.tmpdir")).build();
    }

    private Configuration transactionalConfiguration() {
        return new ConfigurationBuilder().transaction().transactionMode(TransactionMode.TRANSACTIONAL).lockingMode(LockingMode.PESSIMISTIC).build();
    }

}
