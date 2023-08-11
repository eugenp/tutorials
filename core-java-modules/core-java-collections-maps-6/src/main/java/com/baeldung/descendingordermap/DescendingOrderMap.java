package com.baeldung.descendingordermap;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DescendingOrderMap {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueDescending(Map<K, V> map) {
        Comparator<Map.Entry<K, V>> valueComparator = (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue());
        Map<K, V> sortedMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(valueComparator)
                .forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
        return sortedMap;
    }
}
