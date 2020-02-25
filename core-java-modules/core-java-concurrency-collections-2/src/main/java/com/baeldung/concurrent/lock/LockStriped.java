package com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Striped;

public class LockStriped extends ConcurrentAccessMap {
    Striped<Lock> lock;

    public LockStriped(Map<String, String> map) {
        super(map);
        lock = getStripedLock();
    }

    private Striped<Lock> getStripedLock() {
        Striped<Lock> map = Striped.lock(BUCKETS);
        return map;
    }

    protected Supplier<?> putSupplier(int x) {
        return (()-> {
            Lock currentLock = lock.get("key" + x);
            boolean done = false;
            while(!done) {
                done = currentLock.tryLock();
            }
            map.put("key" + x, "value" + x);
            currentLock.unlock();
            return null;
        });
    }

    protected Supplier<?> getSupplier(int x) {
        return (()-> {
            Lock currentLock = lock.get("key" + x);
            boolean done = false;
            while(!done) {
                done = currentLock.tryLock();
            }
            map.get("key" + x);
            currentLock.unlock();
            return null;
        });
    }
}
