package com.baeldung.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class Magic {
    public String nullFunc() {
        return null;
    }

    public String strFunc(String input) {
        return input + ": A nice string";
    }
}

public class PutIfAbsentVsComputeIfAbsentUnitTest {

    private static final Map<String, String> MY_MAP = new HashMap<>();
    private Magic magic = new Magic();

    @BeforeEach
    void resetTheMap() {
        MY_MAP.clear();
        MY_MAP.put("Key A", "value A");
        MY_MAP.put("Key B", "value B");
        MY_MAP.put("Key C", "value C");
        MY_MAP.put("Key Null", null);
    }

    @Test
    void whenCallingPutIfAbsentWithAbsentKey_thenGetNull() {
        String putResult = MY_MAP.putIfAbsent("new key1", magic.nullFunc());
        assertNull(putResult);

        putResult = MY_MAP.putIfAbsent("new key2", magic.strFunc("new key2"));
        assertNull(putResult);

        putResult = MY_MAP.putIfAbsent("Key Null", magic.strFunc("Key Null"));
        assertNull(putResult);
    }

    @Test
    void whenCallingComputeIfAbsentWithAbsentKey_thenGetExpectedResult() {
        String computeResult = MY_MAP.computeIfAbsent("new key1", k -> magic.nullFunc());
        assertNull(computeResult);

        computeResult = MY_MAP.computeIfAbsent("new key2", k -> magic.strFunc(k));
        assertEquals("new key2: A nice string", computeResult);

        computeResult = MY_MAP.computeIfAbsent("Key Null", k -> magic.strFunc(k));
        assertEquals("Key Null: A nice string", computeResult);
    }

    @Test
    void whenCallingPutIfAbsentWithAbsentKey_thenNullIsPut() {
        assertEquals(4, MY_MAP.size()); // initial: 4 entries
        MY_MAP.putIfAbsent("new key", magic.nullFunc());
        assertEquals(5, MY_MAP.size());
        assertTrue(MY_MAP.containsKey("new key")); // new entry has been added to the map
        assertNull(MY_MAP.get("new key"));
    }

    @Test
    void whenCallingComputeIfAbsentWithAbsentKey_thenNullIsNotPut() {
        assertEquals(4, MY_MAP.size()); // initial: 4 entries
        MY_MAP.computeIfAbsent("new key", k -> magic.nullFunc());
        assertEquals(4, MY_MAP.size());
        assertFalse(MY_MAP.containsKey("new key")); // <- no new entry added
    }

    @Test
    void whenCallingPutIfAbsent_thenFunctionIsAlwaysCalled() {
        Magic spyMagic = spy(magic);
        MY_MAP.putIfAbsent("Key A", spyMagic.strFunc("Key A"));
        verify(spyMagic, times(1)).strFunc(anyString());

        MY_MAP.putIfAbsent("new key", spyMagic.strFunc("new key"));
        verify(spyMagic, times(2)).strFunc(anyString());
    }

    @Test
    void whenCallingComputeIfAbsent_thenFunctionIsCalledOnDemand() {
        Magic spyMagic = spy(magic);
        MY_MAP.computeIfAbsent("Key A", k -> spyMagic.strFunc(k));
        verify(spyMagic, never()).strFunc(anyString());

        MY_MAP.computeIfAbsent("new key", k -> spyMagic.strFunc(k));
        verify(spyMagic, times(1)).strFunc(anyString());
    }

}