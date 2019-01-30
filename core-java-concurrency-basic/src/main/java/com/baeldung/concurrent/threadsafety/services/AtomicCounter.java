package com.baeldung.concurrent.threadsafety.services;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    
    private final AtomicInteger counter = new AtomicInteger();
    
    public AtomicCounter() {}
    
    public void incrementCounter() {
        counter.incrementAndGet();
    }
    
    public synchronized int getCounter() {
        return counter.get();
    }
}
