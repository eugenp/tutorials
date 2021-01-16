package com.baeldung.map.entry;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class MapEntryUnitTest {

    @Test
    public void givenSimpleEntryList_whenAddDuplicateKey_thenDoesNotOverwriteExistingKey() {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        entries.add(new AbstractMap.SimpleEntry<>("Joshua Bloch", "Effective Java"));
        entries.add(new AbstractMap.SimpleEntry<>("Robert C Martin", "Clean Code"));
        entries.add(new AbstractMap.SimpleEntry<>("Robert C Martin", "Clean Architecture"));

        assertEquals(3, entries.size());
        assertEquals("Joshua Bloch", entries.get(0).getKey());
        assertEquals("Robert C Martin", entries.get(1).getKey());
        assertEquals("Robert C Martin", entries.get(2).getKey());
    }

    @Test
    public void givenRegularMap_whenAddDuplicateKey_thenOverwritesExistingKey() {
        Map<String, String> entries = new HashMap<>();
        entries.put("Joshua Bloch", "Effective Java");
        entries.put("Robert C Martin", "Clean Code");
        entries.put("Robert C Martin", "Clean Architecture");

        assertEquals(2, entries.size());
        assertEquals(entries.get("Robert C Martin"), "Clean Architecture");
    }
}
