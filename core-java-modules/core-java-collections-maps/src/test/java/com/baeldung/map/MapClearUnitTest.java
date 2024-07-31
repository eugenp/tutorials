package com.baeldung.map;

import java.util.Map;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapClearUnitTest {
    @Test
    public void givenMap_returnEntryAndClearContent() {
        Map entry = MapClear.returnCopyAndClearMap();
        assertTrue(entry.isEmpty());
    }

    @Test public void givenMap_returnEntryAndRewriteContent() {
        Map entry = MapClear.returnCopyAndRewriteMap();
        assertTrue(!entry.isEmpty());
    }
}