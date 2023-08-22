package com.baeldung.hashmapmultivalues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiValueHashMap<K, V> {
    private final HashMap<K, ArrayList<V>> map;

    // Constructor
    public MultiValueHashMap() {
        map = new HashMap<>();
    }

    public void put(K key, V value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public List<V> get(K key) {
        return map.getOrDefault(key, new ArrayList<>());
    }

    public void remove(K key, V value) {
        map.computeIfPresent(key, (k, v) -> {
            v.remove(value);
            return v;
        });
    }
}