package com.baeldung.map.sort;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.Test;

public class SortMapUnitTest {


    @Test
    public void given_UnsortedMap_whenSortingByValueDescending_thenValuesAreInDescendingOrder() {
        Map<String, Integer> unsortedMap = new HashMap<>();
        unsortedMap.put("one", 1);
        unsortedMap.put("three", 3);
        unsortedMap.put("five", 5);
        unsortedMap.put("two", 2);
        unsortedMap.put("four", 4);

        Map<String, Integer> sortedMap = sortMapByValueDescending(unsortedMap);

        assertEquals(5, sortedMap.size());
        final Iterator<Integer> iterator = sortedMap.values().iterator();
        assertEquals(5, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(3, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(1, (int) iterator.next());
    }

    @Test
    public void given_UnsortedMap_whenUsingTreeMap_thenKeysAreInDescendingOrder() {
        SortedMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.put("one", 1);
        treeMap.put("three", 3);
        treeMap.put("five", 5);
        treeMap.put("two", 2);
        treeMap.put("four", 4);

        assertEquals(5, treeMap.size());
        final Iterator<String> iterator = treeMap.keySet().iterator();
        assertEquals("two", iterator.next());
        assertEquals("three", iterator.next());
        assertEquals("one", iterator.next());
        assertEquals("four", iterator.next());
        assertEquals("five", iterator.next());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueDescending(Map<K, V> map) {
        return map.entrySet().stream()
            .sorted(Map.Entry.<K, V>comparingByValue().reversed())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
