package com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Supplier;

public class CoarseGrained extends ConcurrentAccessMap {
    ReentrantLock lock;

    public CoarseGrained(Map<String, String> map) {
        super(map);
        lock = new ReentrantLock();
    }

    protected Supplier<?> putSupplier(int x) {
        return (()-> {
            boolean done = false;
            while(!done) {
                done = lock.tryLock();
            }
            map.put("key" + x, "value" + x);
            lock.unlock();
            return null;
        });
    }

    protected Supplier<?> getSupplier(int x) {
        return (()-> {
            boolean done = false;
            while(!done) {
                done = lock.tryLock();
            }
            map.get("key" + x);
            lock.unlock();
            return null;
        });
    }
}
