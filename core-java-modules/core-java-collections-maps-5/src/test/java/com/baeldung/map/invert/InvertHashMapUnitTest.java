package com.baeldung.map.invert;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvertHashMapUnitTest {

    Map<String, Integer> sourceMap;

    @BeforeEach
    void setup() {
        sourceMap = new HashMap<>();
        sourceMap.put("Sunday", 0);
        sourceMap.put("Monday", 1);
        sourceMap.put("Tuesday", 2);
        sourceMap.put("Wednesday", 3);
        sourceMap.put("Thursday", 4);
        sourceMap.put("Friday", 5);
        sourceMap.put("Saturday", 6);
    }

    @Test
    void givenSourceMap_whenUsingForLoop_returnsInvertedMap() {
        Map<Integer, String> inversedMap = InvertHashMapExample.invertMapUsingForLoop(sourceMap);
        
        assertNotNull(inversedMap);
        assertEquals(sourceMap.size(), inversedMap.size());
        assertEquals("Monday", inversedMap.get(1));
    }

    @Test
    void givenSourceMap_whenUsingStreams_returnsInvertedMap() {
        Map<Integer, String> inversedMap = InvertHashMapExample.invertMapUsingStreams(sourceMap);
        
        assertNotNull(inversedMap);
        assertEquals(sourceMap.size(), inversedMap.size());
        assertEquals("Monday", inversedMap.get(1));
    }

    @Test
    void givenSourceMap_whenUsingMapper_returnsInvertedMap() {
        Map<Integer, String> inversedMap = InvertHashMapExample.invertMapUsingMapper(sourceMap);
        
        assertNotNull(inversedMap);
        assertEquals(sourceMap.size(), inversedMap.size());
        assertEquals("Monday", inversedMap.get(1));
    }

    @Test
    void givenSourceMapWithDuplicateValues_whenUsingGroupBy_returnsInvertedMap() {
        sourceMap.put("MONDAY", 1);
        Map<Integer, List<String>> inversedMap = InvertHashMapExample.invertMapUsingGroupingBy(sourceMap);
        
        assertNotNull(inversedMap);
        assertNotEquals(sourceMap.size(), inversedMap.size()); // duplicate keys are merged now
        assertEquals(2, inversedMap.get(1).size());
        assertTrue(inversedMap.get(1).contains("Monday"));
        assertTrue(inversedMap.get(1).contains("MONDAY"));
    }

}
