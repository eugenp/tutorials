package com.baeldung.map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class LimitMaxSizeHashMapByLinkedHashMapUnitTest {

    @Test
    void givenLinkedHashMapObject_whenAddingNewEntry_thenEldestEntryIsRemoved() {
        final int MAX_SIZE = 4;
        LinkedHashMap<Integer, String> linkedHashMap;
        linkedHashMap = new LinkedHashMap<Integer, String>() {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > MAX_SIZE;
            }
        };
        linkedHashMap.put(1, "One");
        linkedHashMap.put(2, "Two");
        linkedHashMap.put(3, "Three");
        linkedHashMap.put(4, "Four");
        linkedHashMap.put(5, "Five");
        String[] expectedArrayAfterFive = { "Two", "Three", "Four", "Five" };
        assertArrayEquals(expectedArrayAfterFive, linkedHashMap.values()
            .toArray());
        linkedHashMap.put(6, "Six");
        String[] expectedArrayAfterSix = { "Three", "Four", "Five", "Six" };
        assertArrayEquals(expectedArrayAfterSix, linkedHashMap.values()
            .toArray());
    }

}
