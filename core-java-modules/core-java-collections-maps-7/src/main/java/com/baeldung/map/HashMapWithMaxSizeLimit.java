package com.baeldung.map;

import java.util.HashMap;

public class HashMapWithMaxSizeLimit<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private int maxSize = -1;

    public HashMapWithMaxSizeLimit(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    @Override
    public V put(K key, V value) {
        V res = null;
        if (this.maxSize == -1 || this.size() < this.maxSize) {
            res = super.put(key, value);
        } else if (this.maxSize != -1) {
            throw new RuntimeException("Max size exceeded!");
        }
        return res;
    }

}
