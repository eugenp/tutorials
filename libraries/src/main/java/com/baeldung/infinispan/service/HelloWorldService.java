package com.baeldung.infinispan.service;

import com.baeldung.infinispan.listener.CacheListener;
import com.baeldung.infinispan.repository.HelloWorldRepository;
import org.infinispan.Cache;

import java.util.concurrent.TimeUnit;

public class HelloWorldService {

    private final HelloWorldRepository repository;

    private final Cache<String, String> simpleHelloWorldCache;
    private final Cache<String, String> expiringHelloWorldCache;
    private final Cache<String, String> evictingHelloWorldCache;
    private final Cache<String, String> passivatingHelloWorldCache;

    public HelloWorldService(HelloWorldRepository repository, CacheListener listener, Cache<String, String> simpleHelloWorldCache, Cache<String, String> expiringHelloWorldCache, Cache<String, String> evictingHelloWorldCache,
            Cache<String, String> passivatingHelloWorldCache) {

        this.repository = repository;

        this.simpleHelloWorldCache = simpleHelloWorldCache;
        this.expiringHelloWorldCache = expiringHelloWorldCache;
        this.evictingHelloWorldCache = evictingHelloWorldCache;
        this.passivatingHelloWorldCache = passivatingHelloWorldCache;
    }

    public String findSimpleHelloWorld() {
        String cacheKey = "simple-hello";
        return simpleHelloWorldCache.computeIfAbsent(cacheKey, k -> repository.getHelloWorld());
    }

    public String findExpiringHelloWorld() {
        String cacheKey = "expiring-hello";
        String helloWorld = simpleHelloWorldCache.get(cacheKey);
        if (helloWorld == null) {
            helloWorld = repository.getHelloWorld();
            simpleHelloWorldCache.put(cacheKey, helloWorld, 1, TimeUnit.SECONDS);
        }
        return helloWorld;
    }

    public String findIdleHelloWorld() {
        String cacheKey = "idle-hello";
        String helloWorld = simpleHelloWorldCache.get(cacheKey);
        if (helloWorld == null) {
            helloWorld = repository.getHelloWorld();
            simpleHelloWorldCache.put(cacheKey, helloWorld, -1, TimeUnit.SECONDS, 10, TimeUnit.SECONDS);
        }
        return helloWorld;
    }

    public String findSimpleHelloWorldInExpiringCache() {
        String cacheKey = "simple-hello";
        String helloWorld = expiringHelloWorldCache.get(cacheKey);
        if (helloWorld == null) {
            helloWorld = repository.getHelloWorld();
            expiringHelloWorldCache.put(cacheKey, helloWorld);
        }
        return helloWorld;
    }

    public String findEvictingHelloWorld(String key) {
        String value = evictingHelloWorldCache.get(key);
        if (value == null) {
            value = repository.getHelloWorld();
            evictingHelloWorldCache.put(key, value);
        }
        return value;
    }

    public String findPassivatingHelloWorld(String key) {
        return passivatingHelloWorldCache.computeIfAbsent(key, k -> repository.getHelloWorld());
    }

}
