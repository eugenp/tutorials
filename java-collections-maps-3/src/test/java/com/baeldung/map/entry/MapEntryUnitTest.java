package com.baeldung.map.entry;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class MapEntryUnitTest {

    @Test
    public void givenSimpleEntryList_whenAddDuplicateKey_thenDoesNotOverwriteExistingKey() {
        List<Map.Entry<String, Book>> orderedTuples = new ArrayList<>();
        orderedTuples.add(new AbstractMap.SimpleEntry<>("9780134685991", new Book("Effective Java 3d Edition", "Joshua Bloch")));
        orderedTuples.add(new AbstractMap.SimpleEntry<>("9780132350884", new Book("Clean Code", "Robert C Martin")));
        orderedTuples.add(new AbstractMap.SimpleEntry<>("9780132350884", new Book("Clean Code", "Robert C Martin")));

        assertEquals(3, orderedTuples.size());
        assertEquals("9780134685991", orderedTuples.get(0).getKey());
        assertEquals("9780132350884", orderedTuples.get(1).getKey());
        assertEquals("9780132350884", orderedTuples.get(2).getKey());
    }

    @Test
    public void givenRegularMap_whenAddDuplicateKey_thenOverwritesExistingKey() {
        Map<String, Book> entries = new HashMap<>();
        entries.put("9780134685991", new Book("Effective Java 3d Edition", "Joshua Bloch"));
        entries.put("9780132350884", new Book("Clean Code", "Robert C Martin"));
        entries.put("9780132350884", new Book("Clean Code", "Robert C Martin"));

        assertEquals(2, entries.size());
    }
}
