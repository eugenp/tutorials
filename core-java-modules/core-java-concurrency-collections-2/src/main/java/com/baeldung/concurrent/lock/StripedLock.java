package com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Striped;

public class StripedLock extends ConcurrentAccessExperiment {
    Striped<Lock> lock;

    public StripedLock(int buckets) {
        lock = getStripedLock(buckets);
    }

    private Striped<Lock> getStripedLock(int buckets) {
        Striped<Lock> map = Striped.lock(buckets);
        return map;
    }

    protected synchronized Supplier<?> putSupplier(Map<String,String> map, int key) {
        return (()-> {
            Lock currentLock = lock.get("key" + key);
            boolean done = false;
            try {
                while(!done) {
                    done = currentLock.tryLock();
                }
                map.put("key" + key, "value" + key);
            } finally {
                currentLock.unlock();
            }
            return null;
        });
    }

    protected synchronized Supplier<?> getSupplier(Map<String,String> map, int key) {
        return (()-> {
            Lock currentLock = lock.get("key" + key);
            boolean done = false;
            try {
                while(!done) {
                    done = currentLock.tryLock();
                }
                map.get("key" + key);
            } finally {
                currentLock.unlock();
            }
            return null;
        });
    }
}
