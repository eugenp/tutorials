package com.baeldung.java.map.initialize;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapInitializer {

    public static Map<String, String> articleMapOne;
    static {
        articleMapOne = new HashMap<>();
        articleMapOne.put("ar01", "Intro to Map");
        articleMapOne.put("ar02", "Some article");
    }

    public static Map<String, String> createSingletonMap() {
        Map<String, String> passwordMap = Collections.singletonMap("username1", "password1");
        return passwordMap;

    }

    public Map<String, String> createEmptyMap() {
        Map<String, String> emptyMap = Collections.emptyMap();
        return emptyMap;
    }

    public Map<String, String> createUsingDoubleBrace() {
        Map<String, String> doubleBraceMap = new HashMap<String, String>() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            {
                put("key1", "value1");
                put("key2", "value2");
            }
        };
        return doubleBraceMap;
    }

    public Map<String, String> createMapUsingStreamStringArray() {
        Map<String, String> map = Stream.of(new String[][] { { "Hello", "World" }, { "John", "Doe" }, })
            .collect(Collectors.toMap(data -> data[0], data -> data[1]));

        return map;
    }

    public Map<String, Integer> createMapUsingStreamObjectArray() {
        Map<String, Integer> map = Stream.of(new Object[][] { { "data1", 1 }, { "data2", 2 }, })
            .collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        return map;
    }

    public Map<String, Integer> createMapUsingStreamSimpleEntry() {
        Map<String, Integer> map = Stream.of(new AbstractMap.SimpleEntry<>("idea", 1), new AbstractMap.SimpleEntry<>("mobile", 2))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }

    public Map<String, Integer> createMapUsingStreamSimpleImmutableEntry() {
        Map<String, Integer> map = Stream.of(new AbstractMap.SimpleImmutableEntry<>("idea", 1), new AbstractMap.SimpleImmutableEntry<>("mobile", 2))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }

    public Map<String, String> createImmutableMapWithStreams() {
        Map<String, String> map = Stream.of(new String[][] { { "Hello", "World" }, { "John", "Doe" }, })
            .collect(Collectors.collectingAndThen(Collectors.toMap(data -> data[0], data -> data[1]), Collections::<String, String> unmodifiableMap));
        return map;

    }
}
