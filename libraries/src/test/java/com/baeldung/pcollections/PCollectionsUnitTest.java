package com.baeldung.pcollections;

import org.junit.Test;
import org.pcollections.*;

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
        Map map = new HashMap();
        map.put("mkey1", "mval1");
        map.put("mkey2", "mval2");

        HashPMap<String, String> pmap2 = HashTreePMap.from(map);
        assertEquals(pmap2.size(), 2);
    }

    @Test
    public void whenHashPMapMethods_thenPerformOperations() {

        HashPMap<String, String> pmap = HashTreePMap.empty();
        pmap = pmap.plus("key1", "value1");
        assertEquals(pmap.size(), 1);

        Map map = new HashMap();
        map.put("key2", "val2");
        map.put("key3", "val3");
        pmap = pmap.plusAll(map);

        assertEquals(pmap.size(), 3);

        pmap = pmap.minus("key1");
        assertFalse(pmap.containsKey("key1"));

        pmap = pmap.minusAll(map.keySet());
        assertEquals(pmap.size(), 0);

    }

    @Test
    public void whenTreePVectorMethods_thenPerformOperations() {
        TreePVector pVector = TreePVector.empty();

        pVector = pVector.plus("e1");
        pVector = pVector.plusAll(Arrays.asList("e2", "e3", "e4"));
        assertEquals(4, pVector.size());

        TreePVector pSub = pVector.subList(0, 2);
        assertTrue(pSub.contains("e1") && pSub.contains("e2"));

        TreePVector pVW = (TreePVector) pVector.with(0, "e10");
        assertEquals(pVW.get(0), "e10");

        pVector = pVector.minus("e1");
        TreePVector pV1 = pVector.minusAll(Arrays.asList("e2", "e3"));
        assertEquals(pV1.size(), 1);
    }

    @Test
    public void whenMapPSetMethods_thenPerformOperations() {

        MapPSet pSet = HashTreePSet.empty()
                .plusAll(Arrays.asList("e1","e2","e3","e4"));
        assertEquals(pSet.size(), 4);

        pSet = pSet.minus("e4");
        assertFalse(pSet.contains("e4"));

    }

}
