package com.baeldung.internalaop;

import jakarta.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "selfInjectionAddOne")
public class SelfInjection {

    @Lazy
    @Resource
    private SelfInjection selfInjection;

    private int counter = 0;

    @Cacheable
    public int addOne(int n) {
        counter++;
        return n + 1;
    }

    public int addOneAndDouble(int n) {
        return selfInjection.addOne(n) + selfInjection.addOne(n);
    }

    public int getCounter() {
        return counter;
    }

    @CacheEvict(allEntries = true)
    public void resetCache() {
        counter = 0;
    }
}
