package com.baeldung.map.mapListIteration;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapListIterationUnitTest {

    @Test
    public void whenIterateListMapUsingTraditionalLoop_thenPrintList() {
        List<Map<String, Object>> listOfMaps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
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
                System.out.println("Name: " + key + ", Age: " + value);
            }
        }

        assertEquals(2, listOfMaps.size());
        assertEquals("Jack", map1.get("name"));
        assertEquals(30, map1.get("age"));
        assertEquals("Jones", map2.get("name"));
        assertEquals(25, map2.get("age"));
    }

    @Test
    public void whenIterateListMapUsingStream_thenPrintList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "Jack");
        map1.put("age", 30);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Jones");
        map2.put("age", 25);

        List<Map<String, Object>> listOfMaps = Arrays.asList(map1, map2);

        listOfMaps.stream()
                .flatMap(map -> map.entrySet().stream())
                .forEach(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    System.out.println("Name: " + key + ", Age: " + value);
                });

        assertEquals(2, listOfMaps.size());
        assertEquals("Jack", map1.get("name"));
        assertEquals(30, map1.get("age"));
        assertEquals("Jones", map2.get("name"));
        assertEquals(25, map2.get("age"));
    }

    @Test
    public void whenIterateListMapUsingKeySet_thenPrintList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "Jack");
        map1.put("age", 30);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "Jones");
        map2.put("age", 25);

        List<Map<String, Object>> listOfMaps = Arrays.asList(map1, map2);

        for (Map<String, Object> map : listOfMaps) {
            for (String key : map.keySet()) {
                Object value = map.get(key);
                System.out.println("Name: " + key + ", Age: " + value);
            }
        }

        assertEquals(2, listOfMaps.size());
        assertEquals("Jack", map1.get("name"));
        assertEquals(30, map1.get("age"));
        assertEquals("Jones", map2.get("name"));
        assertEquals(25, map2.get("age"));
    }
}
