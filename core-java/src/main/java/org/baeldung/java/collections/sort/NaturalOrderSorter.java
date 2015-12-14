package org.baeldung.java.collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.baeldubng.java.entity.Fruit;

public class NaturalOrderSorter {
    List<Fruit> fruitlist;
    List<Integer> fruitCodes;
    List<Entry<Integer, Fruit>> fruitEntries;
    Map<Integer, Fruit> sortedFruitMap;

    public List<Fruit> sortHashSet(final Set<Fruit> hFruitSet) {
        fruitlist = new ArrayList<>(hFruitSet);
        Collections.sort(fruitlist);
        return fruitlist;
    }

    public Map<Integer, Fruit> sortHashMapByKeys(final Map<Integer, Fruit> map) {

        fruitCodes = new ArrayList<>(map.keySet());

        return sortAndAddToMap(fruitCodes, map);
    }

    private Map<Integer, Fruit> sortAndAddToMap(final List<Integer> idList, final Map<Integer, Fruit> map) {
        sortedFruitMap = new LinkedHashMap<>();
        Collections.sort(idList);
        for (final int id : idList) {
            sortedFruitMap.put(id, map.get(id));
        }
        return sortedFruitMap;
    }

    public Map<Integer, Fruit> sortHashMapByVals(final Map<Integer, Fruit> map) {
        fruitlist = new ArrayList<>();
        fruitEntries = new ArrayList<>(map.entrySet());
        sortedFruitMap = new LinkedHashMap<>();
        final Map<Fruit, Integer> tempMap = new HashMap<>();
        for (final Entry<Integer, Fruit> e : fruitEntries) {
            fruitlist.add(e.getValue());
            tempMap.put(e.getValue(), e.getKey());
        }
        Collections.sort(fruitlist);
        for (final Fruit fruit : fruitlist) {
            sortedFruitMap.put(tempMap.get(fruit), fruit);
        }
        return sortedFruitMap;
    }
}
