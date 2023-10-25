package com.baeldung.map.linkedhashmapfirstandlastentry;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetFirstAndLastEntryFromLinkedHashMapUnitTest {
    private static final LinkedHashMap<String, String> THE_MAP = new LinkedHashMap<>();

    static {
        THE_MAP.put("key one", "a1 b1 c1");
        THE_MAP.put("key two", "a2 b2 c2");
        THE_MAP.put("key three", "a3 b3 c3");
        THE_MAP.put("key four", "a4 b4 c4");
    }

    @Test
    void whenIteratingEntrySet_thenGetExpectedResult() {
        Entry<String, String> firstEntry = THE_MAP.entrySet().iterator().next();
        assertEquals("key one", firstEntry.getKey());
        assertEquals("a1 b1 c1", firstEntry.getValue());

        Entry<String, String> lastEntry = null;
        for (Entry<String, String> entry : THE_MAP.entrySet()) {
            lastEntry = entry;
        }
        assertNotNull(lastEntry);
        assertEquals("key four", lastEntry.getKey());
        assertEquals("a4 b4 c4", lastEntry.getValue());

    }

    @Test
    void whenConvertingEntriesToArray_thenGetExpectedResult() {

        Entry<String, String>[] theArray = new Entry[THE_MAP.size()];
        THE_MAP.entrySet().toArray(theArray);

        Entry<String, String> firstEntry = theArray[0];
        assertEquals("key one", firstEntry.getKey());
        assertEquals("a1 b1 c1", firstEntry.getValue());

        Entry<String, String> lastEntry = theArray[THE_MAP.size() - 1];
        assertEquals("key four", lastEntry.getKey());
        assertEquals("a4 b4 c4", lastEntry.getValue());
    }


    @Test
    void whenUsingReflection_thenGetExpectedResult() throws NoSuchFieldException, IllegalAccessException {
        Field head = THE_MAP.getClass().getDeclaredField("head");
        head.setAccessible(true);
        Entry<String, String> firstEntry = (Entry<String, String>) head.get(THE_MAP);
        assertEquals("key one", firstEntry.getKey());
        assertEquals("a1 b1 c1", firstEntry.getValue());

        Field tail = THE_MAP.getClass().getDeclaredField("tail");
        tail.setAccessible(true);
        Entry<String, String> lastEntry = (Entry<String, String>) tail.get(THE_MAP);
        assertEquals("key four", lastEntry.getKey());
        assertEquals("a4 b4 c4", lastEntry.getValue());
    }
}