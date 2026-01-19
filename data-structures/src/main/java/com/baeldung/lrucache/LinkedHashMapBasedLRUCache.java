package com.baeldung.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapBasedLRUCache<K,V> extends LinkedHashMap<K,V> {
    private final int capacity;

    public LinkedHashMapBasedLRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}