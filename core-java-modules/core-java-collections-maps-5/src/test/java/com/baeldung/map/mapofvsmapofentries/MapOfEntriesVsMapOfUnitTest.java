package com.baeldung.map.mapofvsmapofentries;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapOfEntriesVsMapOfUnitTest {

    @Test
    void mapOf() {
        // Use Map.of() to create an empty immutable map
        Map<Long, String> map = Map.of();
        assertNotNull(map);

        // Use Map.of() to create an immutable map with one entry
        Map<Long, String> mapWithEntry = Map.of(1L, "value1");
        assertNotNull(mapWithEntry);
        assertThat(mapWithEntry.size()).isEqualTo(1);
        assertThat(mapWithEntry.get(1L)).isEqualTo("value1");
        
        // Test if map is immutable
        try {
            mapWithEntry.put(2L, "value2");
        } catch (UnsupportedOperationException e) {
            assertThat(e).isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Test
    void mapOfEntries() {
        // Use Map.ofEntries() to create an empty immutable map
        Map<Long, String> map = Map.ofEntries();
        assertNotNull(map);

        // Use Map.ofEntries() to create an immutable map with two entries.
        Map<Long, String> longUserMap = Map.ofEntries(Map.entry(1L, "User A"), Map.entry(2L, "User B"));
        assertNotNull(longUserMap);
        assertThat(longUserMap.size()).isEqualTo(2);
        assertThat(longUserMap.get(1L)).isEqualTo("User A");
        assertThat(longUserMap.get(2L)).isEqualTo("User B");
                
        // Test if map is immutable
        try {
            longUserMap.put(3L, "User C");
        } catch (UnsupportedOperationException e) {
            assertThat(e).isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
