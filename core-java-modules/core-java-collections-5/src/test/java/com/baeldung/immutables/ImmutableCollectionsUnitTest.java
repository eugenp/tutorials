package com.baeldung.immutables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ImmutableCollectionsUnitTest {

    @Test
    void givenUnmodifiableMap_whenPutNewEntry_thenThrowsUnsupportedOperationException() {
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("name1", "Michael");
        modifiableMap.put("name2", "Harry");

        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("name3", "Micky"));
    }

    @Test
    void givenUnmodifiableMap_whenPutNewEntryUsingOriginalReference_thenSuccess() {
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("name1", "Michael");
        modifiableMap.put("name2", "Harry");

        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);
        modifiableMap.put("name3", "Micky");

        assertEquals(modifiableMap, unmodifiableMap);
        assertTrue(unmodifiableMap.containsKey("name3"));
    }

    @Test
    void givenImmutableMap_whenPutNewEntry_thenThrowsUnsupportedOperationException() {
        Map<String, String> immutableMap = Map.of("name1", "Michael", "name2", "Harry");

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("name3", "Micky"));
    }

    @Test
    void givenImmutableMap_whenUsecopyOf_thenExceptionOnPut() {
        Map<String, String> immutableMap = Map.of("name1", "Michael", "name2", "Harry");
        Map<String, String> copyOfImmutableMap = Map.copyOf(immutableMap);

        assertThrows(UnsupportedOperationException.class, () -> copyOfImmutableMap.put("name3", "Micky"));
    }
}

