package com.baeldung.map.randommapkey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class RandomKeyShuffleMap {

    static <K, V> K getRandomKeyUsingShuffle(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        List<K> keys = new ArrayList<>(map.keySet());
        Collections.shuffle(keys);

        return keys.get(0);
    }
}

