package com.baeldung.atomicvsvolatile;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeAtomicCounter {

    private final AtomicInteger counter = new AtomicInteger(0);
    
    public int getValue() {
        return counter.get();
    }
    
    public void increment() {
        counter.incrementAndGet();
    }
}
