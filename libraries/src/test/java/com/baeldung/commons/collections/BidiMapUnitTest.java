package com.baeldung.commons.collections;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by smatt on 03/07/2017.
 */
public class BidiMapUnitTest {

    @Test
    public void givenKeyValue_whenPut_thenAddEntryToMap() {
        BidiMap<String, String> map = new DualHashBidiMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(map.size(), 2);
    }

    @Test
    public void whenInverseBidiMap_thenInverseKeyValue() {
        BidiMap<String, String> map = new DualHashBidiMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        BidiMap<String, String> rMap = map.inverseBidiMap();
        assertTrue(rMap.containsKey("value1") && rMap.containsKey("value2"));
    }

    @Test
    public void givenValue_whenRemoveValue_thenRemoveMatchingMapEntry() {
        BidiMap<String, String> map = new DualHashBidiMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.removeValue("value2");
        assertFalse(map.containsKey("key2"));
    }

    @Test
    public void givenValue_whenGetKey_thenMappedKey() {
        BidiMap<String, String> map = new DualHashBidiMap<>();
        map.put("key1", "value1");
        assertEquals(map.getKey("value1"), "key1");
    }
}
