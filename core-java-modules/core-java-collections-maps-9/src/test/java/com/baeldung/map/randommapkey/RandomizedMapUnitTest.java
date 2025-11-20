package com.baeldung.map.randommapkey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class RandomizedMapUnitTest {

    @Test
    public void whenGettingRandomValue_thenValueExistsInMap() {
        RandomizedMap<String, Integer> map = new RandomizedMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        Integer randomValue = map.getRandomValue();

        assertNotNull(randomValue);
        assertTrue(Arrays.asList(1, 2, 3).contains(randomValue));
    }

    @Test
    public void whenRemovingValue_thenRandomValueDoesNotContainRemovedEntry() {
        RandomizedMap<String, Integer> map = new RandomizedMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        map.remove("banana");

        for (int i = 0; i < 20; i++) {
            Integer randomValue = map.getRandomValue();
            assertNotNull(randomValue);
            assertFalse(randomValue.equals(2));
        }
    }

    @Test
    public void whenMapIsEmpty_thenReturnsNull() {
        RandomizedMap<String, Integer> map = new RandomizedMap<>();

        Integer randomValue = map.getRandomValue();

        assertNull(randomValue);
    }

    @Test
    public void whenClearingMap_thenRandomValueReturnsNull() {
        RandomizedMap<String, Integer> map = new RandomizedMap<>();
        map.put("apple", 1);
        map.put("banana", 2);

        map.clear();

        Integer randomValue = map.getRandomValue();
        assertNull(randomValue);
    }
}

