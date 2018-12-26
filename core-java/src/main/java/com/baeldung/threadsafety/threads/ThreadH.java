package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.ReentrantReadWriteLockCounter;

public class ThreadH extends Thread {
    
    private final ReentrantReadWriteLockCounter counter;

    public ThreadH(ReentrantReadWriteLockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public void run() {
        counter.incrementCounter();
        System.out.println(counter.getCounter());
    }
    
    public ReentrantReadWriteLockCounter getReentrantReadWriteLockCounter() {
        return counter;
    }
}
