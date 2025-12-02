package com.baeldung.map.randommapkey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RandomNumberMapUnitTest {

    private final RandomNumberMap randomNumberMap = new RandomNumberMap();

    @Test
    public void whenGettingRandomValue_thenValueExistsInMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.put("date", 4);
        map.put("elderberry", 5);

        Integer randomValue = randomNumberMap.getRandomValueUsingList(map);

        assertNotNull(randomValue);
        assertTrue(map.containsValue(randomValue));
    }

    @Test
    public void whenGettingRandomValueMultipleTimes_thenAllValuesAreFromMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        Set<Integer> retrievedValues = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Integer value = randomNumberMap.getRandomValueUsingOffset(map);
            retrievedValues.add(value);
        }

        assertTrue(map.values().containsAll(retrievedValues));
    }

    @Test
    public void whenMapIsEmpty_thenReturnsNull() {
        Map<String, Integer> emptyMap = new HashMap<>();

        Integer result = randomNumberMap.getRandomValueUsingList(emptyMap);

        assertNull(result);
    }

    @Test
    public void whenMapIsNull_thenReturnsNull() {
        Integer result = randomNumberMap.getRandomValueUsingList(null);

        assertNull(result);
    }

    @Test
    public void whenMapHasSingleElement_thenReturnsThatElement() {
        Map<String, Integer> singleEntryMap = new HashMap<>();
        singleEntryMap.put("only", 42);

        Integer result = randomNumberMap.getRandomValueUsingList(singleEntryMap);

        assertEquals(Integer.valueOf(42), result);
    }

    @Test
    public void whenUsingOffsetWithSingleElement_thenReturnsThatElement() {
        Map<String, Integer> singleEntryMap = new HashMap<>();
        singleEntryMap.put("only", 42);

        Integer result = randomNumberMap.getRandomValueUsingOffset(singleEntryMap);

        assertEquals(Integer.valueOf(42), result);
    }

    @Test
    public void whenUsingOffset_thenValueExistsInMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.put("date", 4);

        Integer randomValue = randomNumberMap.getRandomValueUsingOffset(map);

        assertNotNull(randomValue);
        assertTrue(map.containsValue(randomValue));
    }
}

