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

    public HelloWorldService(HelloWorldRepository repository, CacheListener listener,
                             Cache<String, String> simpleHelloWorldCache,
                             Cache<String, String> expiringHelloWorldCache,
                             Cache<String, String> evictingHelloWorldCache,
                             Cache<String, String> passivatingHelloWorldCache) {

        this.repository = repository;

        this.simpleHelloWorldCache = simpleHelloWorldCache;
        this.expiringHelloWorldCache = expiringHelloWorldCache;
        this.evictingHelloWorldCache = evictingHelloWorldCache;
        this.passivatingHelloWorldCache = passivatingHelloWorldCache;
    }

    public String findSimpleHelloWorld() {
        String cacheKey = "simple-hello";
        String helloWorld = simpleHelloWorldCache.get(cacheKey);
        if (helloWorld == null) {
            helloWorld = repository.getHelloWorld();
            simpleHelloWorldCache.put(cacheKey, helloWorld);
        }
        return helloWorld;
    }

    public String findExpiringHelloWorld() {
        String cacheKey = "expiring-hello";
        String helloWorld = simpleHelloWorldCache.get(cacheKey);
        if (helloWorld == null) {
            helloWorld = repository.getHelloWorld();
            simpleHelloWorldCache.put(cacheKey, helloWorld, 10, TimeUnit.SECONDS);
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

    public String findEvictingHelloWorld() {
        evictingHelloWorldCache.put("evictable-entry", "Evicting Victim");

        String hello = "Hello World";
        evictingHelloWorldCache.put("hello-entry", hello);

        if(evictingHelloWorldCache.get("evictable-entry") == null) {
            System.out.println("The first entry was evicted, therefore couldn't be retrieved");
        }

        return hello;
    }

    public String findPassivatingHelloWorld() {
        passivatingHelloWorldCache.put("evictable-entry", "Evicting Victim");

        String hello = "Hello World";
        passivatingHelloWorldCache.put("hello-entry", hello);

        if(passivatingHelloWorldCache.get("evictable-entry") != null) {
            System.out.println("It was possible to retrieve the evicted entry");
        }
        return hello;
    }

}
