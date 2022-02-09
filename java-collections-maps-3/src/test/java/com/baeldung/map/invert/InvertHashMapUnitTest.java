package com.baeldung.map.invert;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvertHashMapUnitTest {

    Map<String, Integer> sourceMap;

    @BeforeAll
    void setup() {
        sourceMap = new HashMap<>();
        sourceMap.put("Sunday", 0);
        sourceMap.put("Monday", 1);
    }

    @Test
    void test1_invertMapUsingForLoop() {
        Map<Integer, String> inversedMap = InvertHashMapExample.invertMapUsingForLoop(sourceMap);
        assertNotNull(inversedMap);
        assertEquals(inversedMap.size(), 2);
        assertEquals("Monday", inversedMap.get(1));
    }

    @Test
    void test2_invertMapUsingStreams() {
        Map<Integer, String> inversedMap = InvertHashMapExample.invertMapUsingStreams(sourceMap);
        assertNotNull(inversedMap);
        assertEquals(inversedMap.size(), 2);
        assertEquals("Monday", inversedMap.get(1));
    }

    @Test
    void test3_invertMapUsingMapper() {
        Map<Integer, String> inversedMap = InvertHashMapExample.invertMapUsingMapper(sourceMap);
        assertNotNull(inversedMap);
        assertEquals(inversedMap.size(), 2);
        assertEquals("Monday", inversedMap.get(1));
    }

    @Test
    void test4_invertMapUsingGroupingBy() {
        sourceMap.put("monday", 1);
        Map<Integer, List<String>> inversedMap = InvertHashMapExample.invertMapUsingGroupingBy(sourceMap);
        assertNotNull(inversedMap);
        assertEquals(inversedMap.size(), 2);
        assertTrue(inversedMap.get(1) instanceof List);
    }

}
