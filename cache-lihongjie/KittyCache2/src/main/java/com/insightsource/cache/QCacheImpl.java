package com.insightsource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1. ѡ��HashMap������ConcurrentHashMap����Ϊ��ConcurrentHashMap�޷�ʵ��getAll()����
 * 2. ���еĲ����������ˣ����Ҳ����ҪConcurrentHashMap����֤�̰߳�ȫ����
 * 3. Ϊ���������ܣ�ʹ���˶�д����������������ѯ����
 */
public class QCacheImpl<K, V> implements QCache<K, V> {
    private final Map<K, V> cache;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();


    public QCacheImpl() {
        cache = new HashMap<K, V>();
    }

    public QCacheImpl(int initialCapacity) {
        cache = new HashMap<K, V>(initialCapacity);
    }

    @Override
    public V get(K key) {
        readLock.lock();
        try {
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        writeLock.lock();
        try {
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void remove(K key) throws Exception {
        writeLock.lock();
        try {
            if (!isPresent(key)) {
                throw new Exception("Cache Entries Not Exist.");
            }

            cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            cache.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int size() {
        readLock.lock();
        try {
            return cache.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys) throws Exception {
        readLock.lock();
        try {
            Map<K, V> map = new HashMap<K, V>();
            List<K> noEntryKeys = new ArrayList<K>();
            while (keys.hasNext()) {
                K key = keys.next();
                if (isPresent(key)) {
                    map.put(key, cache.get(key));
                } else {
                    noEntryKeys.add(key);
                }
            }

            if (!noEntryKeys.isEmpty()) {
                throw new Exception("Cache Entries Not Exist.");
            }

            return map;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> entries) {
        writeLock.lock();
        try {
            cache.putAll(entries);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeAll(Iterator<? extends K> keys) {
        writeLock.lock();
        try {
            cache.clear();
        } finally {
            writeLock.unlock();
        }
    }

    private boolean isPresent(K key) {
        readLock.lock();
        try {
            return cache.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }
}
