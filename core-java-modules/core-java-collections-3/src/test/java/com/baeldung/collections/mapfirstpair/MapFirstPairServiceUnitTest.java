package com.baeldung.collections.mapfirstpair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MapFirstPairServiceUnitTest {

    private MapFirstPairService mapFirstPairService;

    @Before
    public void Setup() {

        mapFirstPairService = new MapFirstPairServiceImpl();
    }

    private void populateMapValues(Map<Integer, String> map) {
        if (map != null) {
            map.put(5, "A");
            map.put(1, "B");
            map.put(2, "C");
        }
    }

    @Test
    public void whenUsingIteratorForHashMap() {
        Map<Integer, String> hashMap = new HashMap<>();
        populateMapValues(hashMap);

        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingStreamForHashMap() {
        Map<Integer, String> hashMap = new HashMap<>();
        populateMapValues(hashMap);
        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingIteratorForLinkedHashMap() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        populateMapValues(linkedHashMap);
        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenUsingStreamForLinkedHashMap() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        populateMapValues(linkedHashMap);

        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingIterator() {
        Map<Integer, String> hashMap = new HashMap<>();
        populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingStream() {
        Map<Integer, String> hashMap = new HashMap<>();
        populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingIterator() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingStream() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairService.getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }
}
