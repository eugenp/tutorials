package com.baeldung.pcollections;

import org.junit.Test;
import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PCollectionsUnitTest {

    @Test
    public void whenEmpty_thenCreateEmptyHashPMap() {
        HashPMap<String, String> pmap = HashTreePMap.empty();
        assertEquals(pmap.size(), 0);
    }

    @Test
    public void givenKeyValue_whenSingleton_thenCreateNonEmptyHashPMap() {
        HashPMap<String, String> pmap1 = HashTreePMap.singleton("key1", "value1");
        assertEquals(pmap1.size(), 1);
    }

    @Test
    public void givenExistingHashMap_whenFrom_thenCreateHashPMap() {
        Map<String, String> map = new HashMap<>();
        map.put("mkey1", "mval1");
        map.put("mkey2", "mval2");

        HashPMap<String, String> pmap2 = HashTreePMap.from(map);
        assertEquals(pmap2.size(), 2);
    }

    @Test
    public void whenHashPMapMethods_thenPerformOperations() {

        HashPMap<String, String> pmap = HashTreePMap.empty();
        HashPMap<String, String> pmap0 = pmap.plus("key1", "value1");

        Map<String, String> map = new HashMap<>();
        map.put("key2", "val2");
        map.put("key3", "val3");

        HashPMap<String, String> pmap1 = pmap0.plusAll(map);
        HashPMap<String, String> pmap2 = pmap1.minus("key1");
        HashPMap<String, String> pmap3 = pmap2.minusAll(map.keySet());

        assertEquals(pmap0.size(), 1);
        assertEquals(pmap1.size(), 3);
        assertFalse(pmap2.containsKey("key1"));
        assertEquals(pmap3.size(), 0);
    }

    @Test
    public void whenTreePVectorMethods_thenPerformOperations() {
        TreePVector<String> pVector = TreePVector.empty();

        TreePVector<String> pV1 = pVector.plus("e1");
        TreePVector<String> pV2 = pV1.plusAll(Arrays.asList("e2", "e3", "e4"));

        assertEquals(1, pV1.size());
        assertEquals(4, pV2.size());

        TreePVector<String> pV3 = pV2.minus("e1");
        TreePVector<String> pV4 = pV3.minusAll(Arrays.asList("e2", "e3", "e4"));

        assertEquals(pV3.size(), 3);
        assertEquals(pV4.size(), 0);

        TreePVector<String> pSub = pV2.subList(0, 2);
        assertTrue(pSub.contains("e1") && pSub.contains("e2"));

        PVector<String> pVW = pV2.with(0, "e10");
        assertEquals(pVW.get(0), "e10");
    }

    @Test
    public void whenMapPSetMethods_thenPerformOperations() {

        MapPSet pSet = HashTreePSet.empty().plusAll(Arrays.asList("e1", "e2", "e3", "e4"));
        assertEquals(pSet.size(), 4);

        MapPSet pSet1 = pSet.minus("e4");
        assertFalse(pSet1.contains("e4"));
    }

}
