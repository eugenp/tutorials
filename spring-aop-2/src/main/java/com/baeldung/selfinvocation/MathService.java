package com.baeldung.selfinvocation;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@CacheConfig(cacheNames = "square")
public class MathService {
    private final AtomicInteger counter = new AtomicInteger();

    @CacheEvict(allEntries = true)
    public AtomicInteger resetCounter() {
        counter.set(0);
        return counter;
    }

    @Cacheable(key = "#n")
    public double square(double n) {
        counter.incrementAndGet();
        return n * n;
    }

    public double sumOfSquareOf2() {
        return this.square(2) + this.square(2);
    }

}

