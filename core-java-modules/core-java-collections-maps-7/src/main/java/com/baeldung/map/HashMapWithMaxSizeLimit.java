package com.baeldung.map;

import java.util.HashMap;

public class HashMapWithMaxSizeLimit<K, V> extends HashMap<K, V> {
    private int maxSize = -1;
    
    public HashMapWithMaxSizeLimit() {
        super();
    }

    public HashMapWithMaxSizeLimit(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    @Override
    public V put(K key, V value) {
        if (this.maxSize == -1 || this.containsKey(key) || this.size() < this.maxSize) {
            return super.put(key, value);
        }
        throw new RuntimeException("Max size exceeded!");
    }

}
