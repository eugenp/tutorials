package com.baeldung.map.keysetValuesEntrySet;

import org.junit.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntrySetExampleUnitTest {

    @Test
    public void givenHashMap_whenEntrySetApplied_thenShouldReturnSetOfEntries() {

        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);

        Set<Map.Entry<String, Integer>> actualValues = map.entrySet();

        assertEquals(2, actualValues.size());
        assertTrue(actualValues.contains(new SimpleEntry<>("one", 1)));
        assertTrue(actualValues.contains(new SimpleEntry<>("two", 2)));

    }

}
