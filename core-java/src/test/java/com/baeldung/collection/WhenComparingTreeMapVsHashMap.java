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
    public void whenInsertObjects_thenNaturalOrder() {
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
    public void whenInsertObjects_thenRandomOrder() {
        Map<Integer, String> treemap = new TreeMap<>();
        treemap.put(3, "TreeMap");
        treemap.put(2, "vs");
        treemap.put(1, "HashMap");
        Assert.assertThat(treemap.keySet(), Matchers.contains(1, 2, 3));
    }

@Test
public void whenInsertNullInHashMap_thenInsertsNull() {
    Map<Integer, String> hashmap = new HashMap<>();
    hashmap.put(null, null);
    Assert.assertNull(hashmap.get(null));
}

    @Test(expected = NullPointerException.class)
    public void givenTree_whenputNullKeyValue_thenNullPointer() {
        Map<Integer, String> tree = new TreeMap<>();
        tree.put(1, "Baeldung");
        tree.put(2, "is");
        tree.put(null, null);
    }

    @Test
    public void givenHash_whenputNullKeyValue_thenOK() {
        Map<Integer, String> hash = new HashMap<>();
        hash.put(1, "Baeldung");
        hash.put(2, "is");
        hash.put(null, null);
        Assert.assertEquals(3, hash.size());
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

    @Test(expected = ConcurrentModificationException.class)
    public void givenHashMap_whenModifyWhenIterator_thenFailFast() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "Baeldung");
        hashMap.put(2, "Baeldung");
        Iterator<Integer> it = hashMap.keySet()
            .iterator();

        while (it.hasNext()) {
            hashMap.remove(it.next());
            it.next();
        }
    }
}
