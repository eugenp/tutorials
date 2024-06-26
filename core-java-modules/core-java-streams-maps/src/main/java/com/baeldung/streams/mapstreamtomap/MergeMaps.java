package com.baeldung.streams.mapstreamtomap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MergeMaps {

    // Method using a simple for loop
    public static <K, V> Map<K, V> mergeMapsUsingLoop(List<Map<K, V>> listOfMaps) {
        Map<K, V> result = new HashMap<>();
        for (Map<K, V> map : listOfMaps) {
            result.putAll(map);
        }
        return result;
    }

    // Method using Java streams
    public static <K, V> Map<K, V> mergeMapsUsingStream(List<Map<K, V>> listOfMaps) {
        return listOfMaps.stream()
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
    }
}