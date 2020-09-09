package com.baeldung.collections.mapfirstpair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MapFirstPairUnitTest {

    private MapFirstPairExample mapFirstPairExample;

    @Before
    public void Setup() {

        mapFirstPairExample = new MapFirstPairExample();
    }

    @Test
    public void whenUsingIteratorForHashMap_thenFirstPairWhichWasNotInsertedFirst() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = mapFirstPairExample.populateMapValues(hashMap);

        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingStreamForHashMap_thenFirstPairWhichWasNotInsertedFirst() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = mapFirstPairExample.populateMapValues(hashMap);
        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingIteratorForLinkedHashMap_thenFirstPairWhichWasInsertedFirst() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = mapFirstPairExample.populateMapValues(linkedHashMap);
        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenUsingStreamForLinkedHashMap_thenFirstPairWhichWasInsertedFirst() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = mapFirstPairExample.populateMapValues(linkedHashMap);

        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingIterator() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = mapFirstPairExample.populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingStream() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = mapFirstPairExample.populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingIterator() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = mapFirstPairExample.populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingStream() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = mapFirstPairExample.populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = mapFirstPairExample.getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }
}
