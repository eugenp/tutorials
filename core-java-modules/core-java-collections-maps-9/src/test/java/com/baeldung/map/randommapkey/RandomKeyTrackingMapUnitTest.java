package com.baeldung.map.randommapkey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class RandomKeyTrackingMapUnitTest {

    @Test
    public void whenGettingRandomValue_thenValueExistsInMap() {
        RandomKeyTrackingMap<String, Integer> map = new RandomKeyTrackingMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        Integer randomValue = map.getRandomValue();

        assertNotNull(randomValue);
        assertTrue(Arrays.asList(1, 2, 3).contains(randomValue));
    }

    @Test
    public void whenRemovingValue_thenRandomValueDoesNotContainRemovedEntry() {
        RandomKeyTrackingMap<String, Integer> map = new RandomKeyTrackingMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        map.remove("banana");

        Integer randomValue = map.getRandomValue();

        assertNotNull(randomValue);
        assertTrue(Arrays.asList(1, 3).contains(randomValue));
    }

    @Test
    public void whenRemovingAllEntries_thenRandomValueReturnsNull() {
        RandomKeyTrackingMap<String, Integer> map = new RandomKeyTrackingMap<>();
        map.put("apple", 1);

        Integer valueBeforeRemoval = map.getRandomValue();
        assertEquals(Integer.valueOf(1), valueBeforeRemoval);

        map.remove("apple");

        Integer valueAfterRemoval = map.getRandomValue();
        assertNull(valueAfterRemoval);
    }
}

