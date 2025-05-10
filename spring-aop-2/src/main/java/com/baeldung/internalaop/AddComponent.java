package com.baeldung.internalaop;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "addOne")
public class AddComponent {

    private int counter = 0;

    @Cacheable
    public int addOne(int n) {
        counter++;
        return n + 1;
    }

    public int addOneAndDouble(int n) {
        return addOne(n) + addOne(n);
    }

    public int getCounter() {
        return counter;
    }

    @CacheEvict(allEntries = true)
    public void resetCache() {
        counter = 0;
    }
}
