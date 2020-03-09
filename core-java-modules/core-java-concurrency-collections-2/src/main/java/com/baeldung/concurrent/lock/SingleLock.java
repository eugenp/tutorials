package main.java.com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Supplier;

public class SingleLock extends ConcurrentAccessMap {
    ReentrantLock lock;

    public SingleLock() {
        lock = new ReentrantLock();
    }

    protected synchronized Supplier<?> putSupplier(Map<String,String> map, int key) {
        return (()-> {
            boolean done = false;
            while(!done) {
                done = lock.tryLock();
            }
            map.put("key" + key, "value" + key);
            lock.unlock();
            return null;
        });
    }

    protected synchronized Supplier<?> getSupplier(Map<String,String> map, int key) {
        return (()-> {
            boolean done = false;
            while(!done) {
                done = lock.tryLock();
            }
            map.get("key" + key);
            lock.unlock();
            return null;
        });
    }
}
