package com.baeldung.map.randommapkey;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class OptimizedRandomKeyTrackingMapUnitTest {

    @Test
    public void whenGettingRandomValue_thenValueExistsInMap() {
        OptimizedRandomKeyTrackingMap<String, Integer> map = new OptimizedRandomKeyTrackingMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        Integer randomValue = map.getRandomValue();

        assertNotNull(randomValue);
        assertTrue(Arrays.asList(1, 2, 3).contains(randomValue));
    }

    @Test
    public void whenRemovingValue_thenRandomValueDoesNotContainRemovedEntry() {
        OptimizedRandomKeyTrackingMap<String, Integer> map = new OptimizedRandomKeyTrackingMap<>();
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
        OptimizedRandomKeyTrackingMap<String, Integer> map = new OptimizedRandomKeyTrackingMap<>();
        map.put("apple", 1);

        map.remove("apple");

        Integer valueAfterRemoval = map.getRandomValue();
        assertNull(valueAfterRemoval);
    }
}

