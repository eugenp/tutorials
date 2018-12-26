package com.baeldung.threadsafety.services;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockCounter {
    
    private int counter;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    
    public ReentrantReadWriteLockCounter() {
        this.counter = 0;
    }
    
    public void incrementCounter() {
        readLock.lock();
        try {
            counter += 1;
        } finally {
            readLock.unlock();
        }
    }
    
    public int getCounter() {
        writeLock.lock();
        try {
            return counter;
        } finally {
            writeLock.unlock();
        }
    }
}
