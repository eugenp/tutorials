package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.ExtrinsicLockCounter;

public class ThreadF extends Thread {
    
    private final ExtrinsicLockCounter counter;
    
    public ThreadF(ExtrinsicLockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public void run() {
        counter.incrementCounter();
        System.out.println(counter.getCounter());
    }
    
    public ExtrinsicLockCounter getExtrinsicLockCounter() {
        return counter;
    }
}
