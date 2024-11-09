package com.baeldung.map.maptruevaluecheck;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class MapValuesCheck {

    public static boolean areAllValuesSameWithReduce(Map<String, Integer> map) {
        if (map.isEmpty()) return true;
        int firstValue = map.values().iterator().next();
        return map.values().stream().reduce(true,
                (result, value) -> result && value.equals(firstValue),
                Boolean::logicalAnd);
    }

    public static boolean areAllValuesSameWithSet(Map<String, Integer> map) {
        Set<Integer> uniqueValues = new HashSet<>(map.values());
        return uniqueValues.size() == 1;
    }

    public static boolean areAllValuesSameWithAllMatch(Map<String, Integer> map) {
        if (map.isEmpty()) return true;
        int firstValue = map.values().iterator().next();
        return map.values().stream().allMatch(value -> value.equals(firstValue));
    }

    public static boolean areAllValuesSameWithLoop(Map<String, Integer> map) {
        if (map.isEmpty()) return true;
        int firstValue = map.values().iterator().next();
        for (int value : map.values()) {
            if (value != firstValue) {
                return false;
            }
        }
        return true;
    }
}