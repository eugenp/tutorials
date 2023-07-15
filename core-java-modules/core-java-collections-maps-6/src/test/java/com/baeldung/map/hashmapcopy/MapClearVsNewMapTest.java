package com.baeldung.map.hashmapcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapClearVsNewMapTest {

    @Test
    public void given_EmptyMap_whenUsingMapClear_thenMapIsEmpty() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.clear();
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    public void given_NonEmptyMap_whenCreatingNewMapInstance_thenMapIsEmpty() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map = new HashMap<>();
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    public void given_OriginalMap_whenUsingMapClear_thenOtherReferencesStillPointToClearedMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        Map<String, Integer> originalMap = map;
        map.clear();
        Assertions.assertTrue(originalMap.isEmpty());
    }
}
