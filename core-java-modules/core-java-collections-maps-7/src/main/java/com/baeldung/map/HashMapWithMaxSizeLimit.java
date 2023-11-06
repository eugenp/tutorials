package com.baeldung.map;

import java.util.HashMap;

public class HashMapWithMaxSizeLimit<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private int maxSize = -1;
    
   
    public HashMapWithMaxSizeLimit(int maxSize) {
        super();
        this.maxSize = maxSize;
    }


    public V putWithLimit(K key, V value) throws Exception {
        if (this.maxSize != - 1 && this.size() >= this.maxSize && !this.containsKey(key)) {
            throw new Exception("Max size exceeded!");
        }
        return this.put(key, value);
    }

}
