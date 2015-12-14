package org.baeldung.java.collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.baeldubng.java.entity.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NaturalOrderingTest {

    private Set<Fruit> fruitSet, fruitTreeSet;
    private List<Fruit> fruitList;
    private Fruit f1, f2, f3, f4, f5, f6;
    private Map<Integer, Fruit> fruitMap;
    private NaturalOrderSorter naturalOrderSorter;

    @Before
    public void setup() {
        initializeFruitsWithSizeAndName();
        fruitSet = new HashSet<Fruit>();
        fruitTreeSet = new TreeSet<>();
        fruitMap = new HashMap<>();
        naturalOrderSorter = new NaturalOrderSorter();
    }

    @Test
    public void givenComparablesFruitsHashSet_whensorted_thenOrderedNaturally() {
        Collections.addAll(fruitSet, f1, f2, f3, f4, f5, f6);
        fruitList = naturalOrderSorter.sortHashSet(fruitSet);
        Assert.assertArrayEquals(fruitList.toArray(), new Fruit[] { f2, f5, f1, f4, f3, f6 });
    }

    @Test
    public void givenComparablesFruitsTreeSet_whenAdded_thenOrderedNaturally() {
        Collections.addAll(fruitTreeSet, f1, f2, f3, f4, f5, f6);
        Assert.assertArrayEquals(fruitTreeSet.toArray(), new Fruit[] { f2, f5, f1, f4, f3, f6 });
    }

    @Test
    public void givenHashmap_whenaddedwithComparables_thenOrderKeysNaturally() {
        putFruitsWithIdToMap();
        fruitMap = naturalOrderSorter.sortHashMapByKeys(fruitMap);
        Assert.assertArrayEquals(fruitMap.keySet().toArray(), new Integer[] { 5, 7, 10, 13, 20, 60 });
    }

    @Test
    public void givenHashmap_whenaddedwithComparables_thenOrderValuesNaturally() {
        putFruitsWithIdToMap();
        fruitMap = naturalOrderSorter.sortHashMapByVals(fruitMap);
        final List<Integer> fruitids = new ArrayList<>();
        for (final Entry<Integer, Fruit> e : fruitMap.entrySet()) {
            fruitids.add(e.getValue().getSize());
        }
        Assert.assertArrayEquals(fruitids.toArray(), new Integer[] { 10, 20, 70, 100, 200, 2320 });
    }

    private void initializeFruitsWithSizeAndName() {
        f1 = new Fruit(70, "Aussie");
        f2 = new Fruit(10, "Irish");
        f3 = new Fruit(200, "American");
        f4 = new Fruit(100, "Shimla");
        f5 = new Fruit(20, "American");
        f6 = new Fruit(2320, "Shimla");

    }

    private void putFruitsWithIdToMap() {
        fruitMap.put(10, f1);
        fruitMap.put(20, f2);
        fruitMap.put(5, f3);
        fruitMap.put(7, f4);
        fruitMap.put(13, f5);
        fruitMap.put(60, f6);
    }

}
