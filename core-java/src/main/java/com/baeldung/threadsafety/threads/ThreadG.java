package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.ReentrantLockCounter;

public class ThreadG extends Thread {
    
    private final ReentrantLockCounter counter;

    public ThreadG(ReentrantLockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public void run() {
        counter.incrementCounter();
        System.out.println(counter.getCounter());
    }
    
    public ReentrantLockCounter getReentrantLockCounter() {
        return counter;
    }
}
