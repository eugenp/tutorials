package com.baeldung.collection;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class WhenComparingTreeMapVsHashMap {

    @Test
    public void whenInsertObjectsTreeMap_thenNaturalOrder() {
        Map<Integer, String> treemap = new TreeMap<>();
        treemap.put(3, "TreeMap");
        treemap.put(2, "vs");
        treemap.put(1, "HashMap");
        Assert.assertThat(treemap.keySet(), Matchers.contains(1, 2, 3));
    }

    @Test(expected = NullPointerException.class)
    public void whenInsertNullInTreeMap_thenException() {
        Map<Integer, String> treemap = new TreeMap<>();
        treemap.put(null, "NullPointerException");
    }

    @Test
    public void whenInsertObjectsHashMap_thenRandomOrder() {
        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(3, "TreeMap");
        hashmap.put(2, "vs");
        hashmap.put(1, "HashMap");
        Assert.assertThat(hashmap.keySet(), Matchers.containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void whenInsertNullInHashMap_thenInsertsNull() {
        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(null, null);
        Assert.assertNull(hashmap.get(null));
    }

    @Test
    public void givenHashMapAndTreeMap_whenputDuplicates_thenOnlyUnique() {
        Map<Integer, String> treeMap = new HashMap<>();
        treeMap.put(1, "Baeldung");
        treeMap.put(1, "Baeldung");

        Assert.assertTrue(treeMap.size() == 1);

        Map<Integer, String> treeMap2 = new TreeMap<>();
        treeMap2.put(1, "Baeldung");
        treeMap2.put(1, "Baeldung");

        Assert.assertTrue(treeMap2.size() == 1);
    }
}