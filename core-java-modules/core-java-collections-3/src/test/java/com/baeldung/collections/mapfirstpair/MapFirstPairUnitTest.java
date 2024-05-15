package com.baeldung.collections.mapfirstpair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MapFirstPairUnitTest {

    private Map.Entry<Integer, String> getFirstPairUsingIterator(Map<Integer, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet()
            .iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }

    private Map.Entry<Integer, String> getFirstPairUsingStream(Map<Integer, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }

        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();

        return entrySet.stream()
            .findFirst()
            .get();
    }

    private Map<Integer, String> populateMapValues(Map<Integer, String> map) {
        if (map != null) {
            map.put(5, "A");
            map.put(1, "B");
            map.put(2, "C");
        }
        return map;
    }

    @Test
    public void whenUsingIteratorForHashMap_thenFirstPairWhichWasNotInsertedFirst() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);

        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingStreamForHashMap_thenFirstPairWhichWasNotInsertedFirst() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);
        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingIteratorForLinkedHashMap_thenFirstPairWhichWasInsertedFirst() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);
        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenUsingStreamForLinkedHashMap_thenFirstPairWhichWasInsertedFirst() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);

        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingIterator() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingStream() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingIterator() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingStream() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }
}
