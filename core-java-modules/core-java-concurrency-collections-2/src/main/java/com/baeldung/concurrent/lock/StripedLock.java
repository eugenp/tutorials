package main.java.com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Striped;

public class StripedLock extends ConcurrentAccessMap {
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
            while(!done) {
                done = currentLock.tryLock();
            }
            map.put("key" + key, "value" + key);
            currentLock.unlock();
            return null;
        });
    }

    protected synchronized Supplier<?> getSupplier(Map<String,String> map, int key) {
        return (()-> {
            Lock currentLock = lock.get("key" + key);
            boolean done = false;
            while(!done) {
                done = currentLock.tryLock();
            }
            map.get("key" + key);
            currentLock.unlock();
            return null;
        });
    }
}
