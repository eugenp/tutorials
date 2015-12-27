package org.baeldung.java.collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.baeldung.java.entity.Apple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NaturalOrderingTest {

    private Set<Apple> appleSet, appleTreeSet;
    private List<Apple> appleList;
    private Apple f1, f2, f3, f4, f5, f6;
    private Map<Integer, Apple> appleMap;
    private Sorter naturalOrderSorter;

    @Before
    public void setup() {
        appleMap = new HashMap<>();
        appleSet = new HashSet<Apple>();
        appleTreeSet = new TreeSet<>();
        naturalOrderSorter = new Sorter();
        initializeapplesWithSizeAndName();
        putAppleAndIdsToMap();
    }

    @Test
    public void givenComparablesapplesHashSet_whensorted_thenOrderedNaturally() {
        Collections.addAll(appleSet, f1, f2, f3, f4, f5, f6);
        appleList = naturalOrderSorter.sortFruitHashSet(appleSet);
        Assert.assertArrayEquals(appleList.toArray(), new Apple[] { f2, f5, f1, f4, f3, f6 });
    }

    @Test
    public void givenComparablesapplesTreeSet_whenAdded_thenOrderedNaturally() {
        Collections.addAll(appleTreeSet, f1, f2, f3, f4, f5, f6);
        Assert.assertArrayEquals(appleTreeSet.toArray(), new Apple[] { f2, f5, f1, f4, f3, f6 });
    }

    @Test
    public void givenHashmap_whenaddedwithComparables_thenOrderKeysNaturally() {
        appleMap = naturalOrderSorter.sortHashMapByKeys(appleMap);
        Assert.assertArrayEquals(appleMap.keySet().toArray(), new Integer[] { 5, 7, 10, 13, 20, 60 });
    }

    @Test
    public void givenHashmap_whenaddedToTreeMap_thenOrderKeysNaturally() {
        final TreeMap<Integer, Apple> orderedAppleMap = new TreeMap<>();
        orderedAppleMap.putAll(appleMap);
        Assert.assertArrayEquals(orderedAppleMap.keySet().toArray(), new Integer[] { 5, 7, 10, 13, 20, 60 });
    }

    @Test
    public void givenHashmap_whenaddedwithComparables_thenOrderValuesNaturally() {
        appleMap = naturalOrderSorter.sortHashMapByVals(appleMap);
        final List<Integer> appleids = new ArrayList<>();
        for (final Entry<Integer, Apple> e : appleMap.entrySet()) {
            appleids.add(e.getValue().getSize());
        }
        Assert.assertArrayEquals(appleids.toArray(), new Integer[] { 120, 130, 145, 150, 175, 200 });
    }

    private void initializeapplesWithSizeAndName() {
        f1 = new Apple(145, "Ambrosia Apple");
        f2 = new Apple(120, "Kinnaur Apple");
        f3 = new Apple(175, "Albany Beauty Apple");
        f4 = new Apple(150, "Washington Apple");
        f5 = new Apple(130, "Albany Beauty Apple");
        f6 = new Apple(200, "Washington Apple");
    }

    private void putAppleAndIdsToMap() {
        appleMap.put(10, f1);
        appleMap.put(20, f2);
        appleMap.put(5, f3);
        appleMap.put(7, f4);
        appleMap.put(13, f5);
        appleMap.put(60, f6);
    }

}
