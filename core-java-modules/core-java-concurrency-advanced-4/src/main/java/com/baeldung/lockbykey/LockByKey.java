package com.baeldung.lockbykey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockByKey {
    
    private static class LockWrapper {
        private final Lock lock = new ReentrantLock();
        private final AtomicInteger numberOfThreadsInQueue = new AtomicInteger(1);
        
        private LockWrapper addThreadInQueue() {
            numberOfThreadsInQueue.incrementAndGet(); 
            return this;
        }
        
        private int removeThreadFromQueue() {
            return numberOfThreadsInQueue.decrementAndGet(); 
        }
        
    }
    
    private static ConcurrentHashMap<String, LockWrapper> locks = new ConcurrentHashMap<String, LockWrapper>();
    
    public void lock(String key) {
        LockWrapper lockWrapper = locks.compute(key, (k, v) -> v == null ? new LockWrapper() : v.addThreadInQueue());
        lockWrapper.lock.lock();
    }
    
    public void unlock(String key) {
        LockWrapper lockWrapper = locks.get(key);
        lockWrapper.lock.unlock();
        if (lockWrapper.removeThreadFromQueue() == 0) { 
            // NB : We pass in the specific value to remove to handle the case where another thread would queue right before the removal
            locks.remove(key, lockWrapper);
        }
    }
    
}
