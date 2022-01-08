package com.baeldung.map.identity;

import org.junit.jupiter.api.Test;

import java.util.IdentityHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityHashMapDemonstratorUnitTest {

    @Test
    public void givenIdentityHashMap_whenNewObjectWithSameKey_thenAddsAsNewValue() {
        IdentityHashMap<String, String> identityHashMap = IdentityHashMapDemonstrator.createWithSimpleData();
        String newGenreKey = new String("genre");
        identityHashMap.put(newGenreKey, "Drama");

        assertEquals(5, identityHashMap.size());
        assertEquals("Fantasy", identityHashMap.get("genre"));
        assertEquals("Drama", identityHashMap.get(newGenreKey));
    }
}
