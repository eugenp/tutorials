package com.baeldung.map.maplistiteration;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapListIterationUnitTest {

    @Test
    public void whenIterateListMapUsingTraditionalLoop_thenCountEntries() {
        List<Map<String, Object>> listOfMaps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        int entryCounter = 0;
        map1.put("name", "Jack");
        map1.put("age", 30);
        listOfMaps.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Jones");
        map2.put("age", 25);
        listOfMaps.add(map2);

        for (Map<String, Object> map : listOfMaps) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                entryCounter++;
                System.out.format("%s: %s\n", key, value);
            }
        }

        assertEquals(4, entryCounter);
    }

    @Test
    public void whenIterateListMapUsingStream_thenCountEntries() {
        Map<String, Object> map1 = new HashMap<>();
        AtomicInteger entryCounter = new AtomicInteger();
        map1.put("name", "Jack");
        map1.put("age", 30);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Jones");
        map2.put("age", 25);

        List<Map<String, Object>> listOfMaps = Arrays.asList(map1, map2);

        listOfMaps.stream()
            .flatMap(map -> map.entrySet()
                .stream())
            .forEach(entry -> {
                String key = entry.getKey();
                Object value = entry.getValue();
                entryCounter.incrementAndGet();
                System.out.format("%s: %s\n", key, value);
            });

        assertEquals(4, entryCounter.get());
    }

    @Test
    public void whenIterateListMapUsingKeySet_thenCountEntries() {
        Map<String, Object> map1 = new HashMap<>();
        int entryCounter = 0;
        map1.put("name", "Jack");
        map1.put("age", 30);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Jones");
        map2.put("age", 25);

        List<Map<String, Object>> listOfMaps = Arrays.asList(map1, map2);

        for (Map<String, Object> map : listOfMaps) {
            for (String key : map.keySet()) {
                Object value = map.get(key);
                ++entryCounter;
                System.out.format("%s: %s\n", key, value);
            }
        }

        assertEquals(4, entryCounter);
    }

    @Test
    public void whenIterateListMapUsingForEach_thenCountEntries() {
        Map<String, Object> map1 = new HashMap<>();
        AtomicInteger entryCounter = new AtomicInteger();
        map1.put("name", "Jack");
        map1.put("age", 30);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Jones");
        map2.put("age", 25);

        List<Map<String, Object>> listOfMaps = Arrays.asList(map1, map2);

        listOfMaps.forEach(map -> map.forEach((key, value) -> {
            entryCounter.incrementAndGet();
            System.out.format("%s: %s\n", key, value);
        }));

        assertEquals(4, entryCounter.get());
    }
}
