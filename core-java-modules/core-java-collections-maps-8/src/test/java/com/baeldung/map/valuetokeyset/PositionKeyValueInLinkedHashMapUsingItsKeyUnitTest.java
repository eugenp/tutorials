package com.baeldung.map.valuetokeyset;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PositionKeyValueInLinkedHashMapUsingItsKeyUnitTest {
    LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();

    {
        linkedHashMap.put("apple", 10);
        linkedHashMap.put("orange", 20);
        linkedHashMap.put("banana", 15);
    }

    @Test
    public void givenLinkedHashMap_whenIteratingThroughEntrySet_thenRetrievePositionByKey() {
        int position = 0;
        for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            if (entry.getKey().equals("orange")) {
                assertEquals(1, position);
                return;
            }
            position++;
        }
        fail("Key not found");
    }

    @Test
    public void givenLinkedHashMap_whenUsingJavaStreams_thenRetrievePositionByValue() {
        Optional<String> key = linkedHashMap.keySet().stream()
                .filter(integer -> Objects.equals(integer, "orange"))
                .findFirst();
        assertTrue(key.isPresent());
        key.ifPresent(s -> assertEquals(1, new LinkedList<>(linkedHashMap.keySet()).indexOf(s)));
    }

}
