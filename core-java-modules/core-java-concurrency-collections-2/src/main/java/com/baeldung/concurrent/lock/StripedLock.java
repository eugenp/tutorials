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
                lock.tryLock();
                map.put("key" + key, "value" + key);
            } catch (Exception e) {
                this.putSupplier(map, key);
            } finally {
                try {
                    lock.unlock();
                } catch (Exception e) {}
            }
            return null;
        });
    }

    protected Supplier<?> getSupplier(Map<String,String> map, int key) {
        return (()-> {
            int bucket = key % stripedLock.size();
            Lock lock = stripedLock.get(bucket);
            try {
                lock.tryLock();
                map.get("key" + key);
            } catch (Exception e) {
                this.getSupplier(map, key);
            } finally {
                try {
                    lock.unlock();
                } catch (Exception e) {}
            }
            return null;
        });
    }
}
