package com.baeldung.map.iteration;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class MapIterationUnitTest {

    MapIteration mapIteration = new MapIteration();

    @Test
    void givenMap_whenIterateUsingIteratorAndValues_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingIteratorAndValues(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingEntrySet_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingEntrySet(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateUsingLambda_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingLambda(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateByKeysUsingLambda_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateByKeysUsingLambda(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateValuesUsingLambda_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateValuesUsingLambda(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingIteratorAndEntry_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingIteratorAndEntry(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateUsingIteratorAndKeySet_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingIteratorAndKeySet(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateUsingKeySetAndForeach_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingKeySetAndForeach(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateUsingStreamAPI_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingStreamAPIAndEntrySet(map);
        assertEquals(10, result);
    }

    @Test
    void givenMap_whenIterateKeys_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateKeys(map);
        assertEquals(4, result);
    }

    @Test
    void givenMap_whenIterateValues_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateValues(map);
        assertEquals(6, result);
    }

    @Test
    void givenIterableMap_whenIterateUsingForEach_thenCorrectSum() {
        IterableMap<Integer, Integer> iterableMap = new HashedMap<>();
        iterableMap.put(1, 2);
        iterableMap.put(3, 4);
        long result = mapIteration.iterateUsingMapIteratorApacheCollection(iterableMap);
        assertEquals(10, result);
    }

    @Test
    void givenMutableMap_whenIterateUsingForEach_thenCorrectSum() throws IOException {
        MutableMap<Integer, Integer> mutableMap = UnifiedMap.newMap();
        mutableMap.put(1, 2);
        mutableMap.put(3, 4);
        long result = mapIteration.iterateEclipseMap(mutableMap);
        assertEquals(10, result);
    }

}