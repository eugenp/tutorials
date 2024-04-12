package com.baeldung.map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

class ImmutableMapUnitTest {

    @Test
    void whenCollectionsUnModifiableMapMethod_thenOriginalCollectionChangesReflectInUnmodifiableMap() {

        Map<String, String> mutableMap = new HashMap<>();
        mutableMap.put("USA", "North America");

        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(mutableMap);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("Canada", "North America"));

        mutableMap.remove("USA");
        assertFalse(unmodifiableMap.containsKey("USA"));

        mutableMap.put("Mexico", "North America");
        assertTrue(unmodifiableMap.containsKey("Mexico"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void whenGuavaImmutableMapFromCopyOfMethod_thenOriginalCollectionChangesDoNotReflectInImmutableMap() {

        Map<String, String> mutableMap = new HashMap<>();
        mutableMap.put("USA", "North America");

        ImmutableMap<String, String> immutableMap = ImmutableMap.copyOf(mutableMap);
        assertTrue(immutableMap.containsKey("USA"));

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("Canada", "North America"));

        mutableMap.remove("USA");
        assertTrue(immutableMap.containsKey("USA"));

        mutableMap.put("Mexico", "North America");
        assertFalse(immutableMap.containsKey("Mexico"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void whenGuavaImmutableMapFromBuilderMethod_thenOriginalCollectionChangesDoNotReflectInImmutableMap() {

        Map<String, String> mutableMap = new HashMap<>();
        mutableMap.put("USA", "North America");

        ImmutableMap<String, String> immutableMap = ImmutableMap.<String, String>builder()
            .putAll(mutableMap)
            .put("Costa Rica", "North America")
            .build();
        assertTrue(immutableMap.containsKey("USA"));
        assertTrue(immutableMap.containsKey("Costa Rica"));

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("Canada", "North America"));

        mutableMap.remove("USA");
        assertTrue(immutableMap.containsKey("USA"));

        mutableMap.put("Mexico", "North America");
        assertFalse(immutableMap.containsKey("Mexico"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void whenGuavaImmutableMapFromOfMethod_thenOriginalCollectionChangesDoNotReflectInImmutableMap() {

        ImmutableMap<String, String> immutableMap = ImmutableMap.of("USA", "North America", "Costa Rica", "North America");
        assertTrue(immutableMap.containsKey("USA"));
        assertTrue(immutableMap.containsKey("Costa Rica"));

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("Canada", "North America"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void givenGuavaImmutableMapFromOfEntriesMethodwhenModifyEntry_thenThrowUnsupportedOperationException() {

        ImmutableMap<Integer, String> immutableMap = ImmutableMap.ofEntries(new AbstractMap.SimpleEntry<>(1, "USA"), new AbstractMap.SimpleEntry<>(2, "Canada"));

        assertThrows(UnsupportedOperationException.class, () -> immutableMap.put(2, "Mexico"));
    }

    @Test
    void givenEntrieswhenCreatingGuavaImmutableMapFromOfEntriesMethod_thenCorrect() {

        ImmutableMap<Integer, String> immutableMap = ImmutableMap.ofEntries(new AbstractMap.SimpleEntry<>(1, "USA"));

        assertEquals(1, immutableMap.size());
        assertThat(immutableMap, IsMapContaining.hasEntry(1, "USA"));
    }

    @Test
    void givenGuavaImmutableMapFromOfEntriesMethodwhenEntryKeyExists_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> ImmutableMap.ofEntries(new AbstractMap.SimpleEntry<>(1, "USA"), new AbstractMap.SimpleEntry<>(1, "Canada")));
    }

    @Test
    void givenGuavaImmutableMapFromOfEntriesMethodwhenEntryKeyIsNull_thenThrowNullPointerException() {

        assertThrows(NullPointerException.class, () -> ImmutableMap.ofEntries(new AbstractMap.SimpleEntry<>(null, "USA")));
    }

    @Test
    void givenGuavaImmutableMapFromOfEntriesMethodwhenEntryValueIsNull_thenThrowNullPointerException() {

        assertThrows(NullPointerException.class, () -> ImmutableMap.ofEntries(new AbstractMap.SimpleEntry<>(1, null)));
    }

}
