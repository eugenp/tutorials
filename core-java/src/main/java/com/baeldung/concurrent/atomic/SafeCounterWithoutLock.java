package com.baeldung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeCounterWithoutLock {
    AtomicInteger counter = new AtomicInteger(0);
    
    public int getValue() {
        return counter.get();
    }
    
    public void increment() {
        while(!counter.compareAndSet(getValue(), getValue()+1)) {
            continue;
        }
        return;
    }
}
