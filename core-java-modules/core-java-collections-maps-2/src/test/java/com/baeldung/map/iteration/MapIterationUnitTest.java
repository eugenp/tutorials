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
        long result = mapIteration.iterateUsingEnhancedForLoopAndEntrySet(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateByKeysUsingLambda_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateByKeysUsingLambdaAndForEach(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateValuesUsingLambda_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateValuesUsingLambdaAndForEach(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingIteratorAndEntry_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingIteratorAndEntrySet(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingIteratorAndKeySet_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingIteratorAndKeySet(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingKeySetAndForeach_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingKeySetAndEnhanceForLoop(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingStreamAPI_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateUsingStreamAPIAndEntrySet(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateUsingStreamAPIParallel_thenCorrectSum() throws IOException {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateMapUsingParallelStreamApi(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateKeys_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateKeysUsingKeySetAndEnhanceForLoop(map);
        assertEquals(6, result);
    }

    @Test
    void givenMap_whenIterateValues_thenCorrectSum() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(3, 4);
        long result = mapIteration.iterateValuesUsingValuesMethodAndEnhanceForLoop(map);
        assertEquals(6, result);
    }

    @Test
    void givenIterableMap_whenIterateUsingForEach_thenCorrectSum() {
        IterableMap<Integer, Integer> iterableMap = new HashedMap<>();
        iterableMap.put(1, 2);
        iterableMap.put(3, 4);
        long result = mapIteration.iterateUsingMapIteratorApacheCollection(iterableMap);
        assertEquals(6, result);
    }

    @Test
    void givenMutableMap_whenIterateUsingForEach_thenCorrectSum() throws IOException {
        MutableMap<Integer, Integer> mutableMap = UnifiedMap.newMap();
        mutableMap.put(1, 2);
        mutableMap.put(3, 4);
        long result = mapIteration.iterateEclipseMap(mutableMap);
        assertEquals(6, result);
    }

}