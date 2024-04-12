package com.baeldung.multivaluedmap;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Unit test for MultivaluedMap.
public class MultivaluedMapUnitTest {
    
    // Testing MultivaluedHashMap.
    @Test
    public void givenMultivaluedHashMap_whenEquals_thenTrue() {
        jakarta.ws.rs.core.MultivaluedMap<String, Integer> mulmap = new jakarta.ws.rs.core.MultivaluedHashMap<>();

        // Mapping keys to values.
        mulmap.addAll("first", 1, 2, 3);
        mulmap.add(null, null);

        assertNotNull(mulmap, "The MultivaluedHashMap is null!");
        assertEquals(1, mulmap.getFirst("first"), "The key isn't mapped to the right values!");
        assertEquals(null, mulmap.getFirst(null), "MultivaluedHashMap didn't accept null!");
    }

    // Testing HashMap.
    @Test
    public void givenHashMap_whenEquals_thenTrue() {
        Map<String, Integer> map = new HashMap<>();

        // Putting key-value pairs into our map.
        map.put("first", 1);
        map.put(null, 2);
        map.put("third", null);

        assertNotNull(map, "The HashMap is null!");
        assertEquals(1, map.get("first"), "The key isn't mapped to the right value!");
        assertEquals(2, map.get(null), "HashMap didn't accept null as key!");
        assertEquals(null, map.get("third"), "HashMap didn't accept null value!");
    }
}
