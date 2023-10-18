package com.baeldung.immutables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ImmutableCollectionsExampleTest {

    @Test
    public void givenUnmodifiableMap_whenPutNewEntry_thenThrowsUnsupportedOperationException() {
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("name1", "Michael");
        modifiableMap.put("name2", "Harry");
        
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("name3", "Micky"));
    }

    @Test
    public void givenUnmodifiableMap_whenPutNewEntryUsingOriginalReference_thenSuccessful() {
        Map<String, String> modifiableMap = new HashMap<>();
        modifiableMap.put("name1", "Michael");
        modifiableMap.put("name2", "Harry");
        
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(modifiableMap);
        modifiableMap.put("name3", "Micky");
        
        assertEquals(modifiableMap, unmodifiableMap);
        Assert.assertTrue(unmodifiableMap.containsKey("name3"));
    }

    @Test
    public void givenImmutableMap_whenPutNewEntry_thenThrowsUnsupportedOperationException() {
        Map<String, String> immutableMap = Map.of("name1", "Michael", "name2", "Harry");

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("name3", "Micky"));
    }

    @Test
    public void givenImmutableMap_whenUseCopyOf_thenPutOnCopyAlsoThrowsUnsupportedOperationException() {
        Map<String, String> immutableMap = Map.of("name1", "Michael", "name2", "Harry");
        Map<String, String> copyOfImmutableMap = Map.copyOf(immutableMap);

        assertThrows(UnsupportedOperationException.class, () -> copyOfImmutableMap.put("name3", "Micky"));
    }
}
