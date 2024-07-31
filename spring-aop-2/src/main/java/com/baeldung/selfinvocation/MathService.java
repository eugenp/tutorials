package com.baeldung.selfinvocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@CacheConfig(cacheNames = "square")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MathService {

    @Autowired
    private MathService self;
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

    public double sumOfSquareOf3() {
        return self.square(3) + self.square(3);
    }

}

