package com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Striped;

public class StripedLock extends ConcurrentAccessExperiment {
    Striped<Lock> stripedLock;

    public StripedLock(int buckets) {
        stripedLock = Striped.lock(buckets);
    }

    protected Supplier<?> putSupplier(Map<String,String> map, int key) {
        return (()-> {
            int bucket = key % stripedLock.size();
            Lock lock = stripedLock.get(bucket);
            try {
                lock.lock();
                map.put("key" + key, "value" + key);
            } finally {
                lock.unlock();
            }
            return null;
        });
    }

    protected Supplier<?> getSupplier(Map<String,String> map, int key) {
        return (()-> {
            int bucket = key % stripedLock.size();
            Lock lock = stripedLock.get(bucket);
            try {
                lock.lock();
                map.get("key" + key);
            } finally {
                lock.unlock();
            }
            return null;
        });
    }
}
