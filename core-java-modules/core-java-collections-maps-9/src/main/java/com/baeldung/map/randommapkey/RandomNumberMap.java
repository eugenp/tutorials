package com.baeldung.map.randommapkey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberMap {

    public <K, V> V getRandomValueUsingList(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        List<K> keys = new ArrayList<>(map.keySet());
        K randomKey = keys.get(ThreadLocalRandom.current().nextInt(keys.size()));
        return map.get(randomKey);
    }

    public <K, V> V getRandomValueUsingOffset(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        int randomOffset = ThreadLocalRandom.current().nextInt(map.size());
        Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
        for (int i = 0; i < randomOffset; i++) {
            iterator.next();
        }

        return iterator.next().getValue();
    }
}