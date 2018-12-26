package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.Counter;

public class ThreadE extends Thread {
    
    private final Counter counter;

    public ThreadE(Counter counter) {
        this.counter = counter;
    }
    
    @Override
    public void run() {
        counter.incrementCounter();
        System.out.println(counter.getCounter());
    }
    
    public Counter getCounter() {
        return counter;
    }
}
