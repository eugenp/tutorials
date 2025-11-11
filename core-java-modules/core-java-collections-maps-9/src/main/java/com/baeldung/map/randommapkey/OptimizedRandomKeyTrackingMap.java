package com.baeldung.map.randommapkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class OptimizedRandomKeyTrackingMap<K, V> {

    private final Map<K, V> delegate = new HashMap<>();
    private final List<K> keys = new ArrayList<>();
    private final Map<K, Integer> keyToIndex = new HashMap<>();

    public void put(K key, V value) {
        V previousValue = delegate.put(key, value);
        if (previousValue == null) {
            keys.add(key);
            keyToIndex.put(key, keys.size() - 1);
        }
    }

    public V remove(K key) {
        V removedValue = delegate.remove(key);
        if (removedValue != null) {
            Integer index = keyToIndex.remove(key);
            if (index != null) {
                removeKeyAtIndex(index);
            }
        }
        return removedValue;
    }

    private void removeKeyAtIndex(int index) {
        int lastIndex = keys.size() - 1;
        if (index == lastIndex) {
            keys.remove(lastIndex);
            return;
        }

        K lastKey = keys.get(lastIndex);
        keys.set(index, lastKey);
        keyToIndex.put(lastKey, index);
        keys.remove(lastIndex);
    }

    public V getRandomValue() {
        if (keys.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(keys.size());
        K randomKey = keys.get(randomIndex);
        return delegate.get(randomKey);
    }
}

