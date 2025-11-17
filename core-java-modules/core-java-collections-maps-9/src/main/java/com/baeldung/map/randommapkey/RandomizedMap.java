package com.baeldung.map.randommapkey;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedMap<K, V> extends HashMap<K, V> {
    
    private final Map<Integer, K> numberToKey = new HashMap<>();
    private final Map<K, Integer> keyToNumber = new HashMap<>();
    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key, value);
        
        if (oldValue == null) {
            int number = this.size() - 1;
            numberToKey.put(number, key);
            keyToNumber.put(key, number);
        }
        
        return oldValue;
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public V remove(Object key) {
        V removedValue = super.remove(key);
        
        if (removedValue != null) {
            removeFromTrackingMaps(key);
        }
        
        return removedValue;
    }
    
    @Override
    public boolean remove(Object key, Object value) {
        boolean removed = super.remove(key, value);
        
        if (removed) {
            removeFromTrackingMaps(key);
        }
        
        return removed;
    }
    
    @Override
    public void clear() {
        super.clear();
        numberToKey.clear();
        keyToNumber.clear();
    }
    
    public V getRandomValue() {
        if (this.isEmpty()) {
            return null;
        }
        
        int randomNumber = ThreadLocalRandom.current().nextInt(this.size());
        K randomKey = numberToKey.get(randomNumber);
        return this.get(randomKey);
    }
    
    private void removeFromTrackingMaps(Object key) {
        @SuppressWarnings("unchecked")
        K keyToRemove = (K) key;
        
        Integer numberToRemove = keyToNumber.get(keyToRemove);
        if (numberToRemove == null) {
            return;
        }
        
        int mapSize = this.size();
        int lastIndex = mapSize;
        
        if (numberToRemove == lastIndex) {
            numberToKey.remove(numberToRemove);
            keyToNumber.remove(keyToRemove);
        } else {
            K lastKey = numberToKey.get(lastIndex);
            if (lastKey == null) {
                numberToKey.remove(numberToRemove);
                keyToNumber.remove(keyToRemove);
                return;
            }
            
            numberToKey.put(numberToRemove, lastKey);
            keyToNumber.put(lastKey, numberToRemove);
            
            numberToKey.remove(lastIndex);
            
            keyToNumber.remove(keyToRemove);
        }
    }
}