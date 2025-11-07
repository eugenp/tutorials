package com.baeldung.map.randommapkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class RandomKeyTrackingMap<K, V> {

    private final Map<K, V> delegate = new HashMap<>();
    private final List<K> keys = new ArrayList<>();

    void put(K key, V value) {
        V previousValue = delegate.put(key, value);
        if (previousValue == null) {
            keys.add(key);
        }
    }

    V remove(K key) {
        V removedValue = delegate.remove(key);
        if (removedValue != null) {
            int index = keys.indexOf(key);
            if (index >= 0) {
                keys.remove(index);
            }
        }
        return removedValue;
    }

    V getRandomValue() {
        if (keys.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(keys.size());
        K randomKey = keys.get(randomIndex);
        return delegate.get(randomKey);
    }

}

