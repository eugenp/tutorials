package com.baeldung.immutables;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class ImmutableCollectionsUnitTest {

    @Test
    public void givenUnmodifiableMap_WhenPutNewEntry_ThenThrowsUnsupportedOperationException() {
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("name1", "Michael");
        modifiableMap.put("name2", "Harry");

        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("name3", "Micky"));
    }

    @Test
    public void givenUnmodifiableMap_WhenPutNewEntryUsingOriginalReference_ThenSuccess() {
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("name1", "Michael");
        modifiableMap.put("name2", "Harry");

        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);
        modifiableMap.put("name3", "Micky");

        assertEquals(modifiableMap, unmodifiableMap);
        assertTrue(unmodifiableMap.containsKey("name3"));
    }

    @Test
    public void givenImmutableMap_WhenPutNewEntry_ThenThrowsUnsupportedOperationException() {
        Map<String, String> immutableMap = Map.of("name1", "Michael", "name2", "Harry");

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("name3", "Micky"));
    }

    @Test
    public void givenImmutableMap_WhenUsecopyOf_ThenExceptionOnPut() {
        Map<String, String> immutableMap = Map.of("name1", "Michael", "name2", "Harry");
        Map<String, String> copyOfImmutableMap = Map.copyOf(immutableMap);

        assertThrows(UnsupportedOperationException.class, () -> copyOfImmutableMap.put("name3", "Micky"));
    }
}
