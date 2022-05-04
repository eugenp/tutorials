package com.baeldung.map.invert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class InvertHashMapExample {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();
        map.put("first", 1);
        map.put("second", 2);
        System.out.println(map);

        invertMapUsingForLoop(map);
        invertMapUsingStreams(map);
        invertMapUsingMapper(map);

        map.put("two", 2);
        invertMapUsingGroupingBy(map);
    }

    public static <V, K> Map<V, K> invertMapUsingForLoop(Map<K, V> map) {
        Map<V, K> inversedMap = new HashMap<V, K>();
        for (Entry<K, V> entry : map.entrySet()) {
            inversedMap.put(entry.getValue(), entry.getKey());
        }
        System.out.println(inversedMap);
        return inversedMap;
    }

    public static <V, K> Map<V, K> invertMapUsingStreams(Map<K, V> map) {
        Map<V, K> inversedMap = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
        System.out.println(inversedMap);
        return inversedMap;
    }

    public static <K, V> Map<V, K> invertMapUsingMapper(Map<K, V> sourceMap) {
        Map<V, K> inversedMap = sourceMap.entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey, (oldValue, newValue) -> oldValue));
        System.out.println(inversedMap);
        return inversedMap;
    }

    public static <V, K> Map<V, List<K>> invertMapUsingGroupingBy(Map<K, V> map) {
        Map<V, List<K>> inversedMap = map.entrySet()
            .stream()
            .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
        System.out.println(inversedMap);
        return inversedMap;
    }

}
