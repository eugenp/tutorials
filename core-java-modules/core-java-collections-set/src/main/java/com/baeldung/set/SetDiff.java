package com.baeldung.set;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SetDiff {

    public static <T> Set<T> findSymmetricDiff(Set<T> set1, Set<T> set2) {
        Map<T, Integer> map = new HashMap<>();
        set1.forEach(e -> putKey(map, e));
        set2.forEach(e -> putKey(map, e));
        return map.entrySet().stream()
            .filter(e -> e.getValue() == 1)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    private static <T> void putKey(Map<T, Integer> map, T key) {
        if (map.containsKey(key)) {
            map.replace(key, Integer.MAX_VALUE);
        } else {
            map.put(key, 1);
        }
    }

}
