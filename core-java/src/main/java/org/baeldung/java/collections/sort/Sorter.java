package org.baeldung.java.collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.baeldung.java.entity.Apple;
import org.baeldung.java.entity.Student;

public class Sorter {
    List<Apple> fruitlist;
    List<Integer> fruitCodes;
    List<Entry<Integer, Apple>> fruitEntries;
    Map<Integer, Apple> orderedFruitMap;

    public List<Apple> sortFruitHashSet(final Set<Apple> hFruitSet) {
        fruitlist = new ArrayList<>(hFruitSet);
        Collections.sort(fruitlist);
        return fruitlist;
    }

    public Map<Integer, Apple> sortHashMapByKeys(final Map<Integer, Apple> map) {

        fruitCodes = new ArrayList<>(map.keySet());
        Collections.sort(fruitCodes);
        orderedFruitMap = new LinkedHashMap<>();
        for (final int id : fruitCodes) {
            orderedFruitMap.put(id, map.get(id));
        }
        return orderedFruitMap;
    }

    public Map<Integer, Apple> sortHashMapByVals(final Map<Integer, Apple> map) {
        fruitlist = new ArrayList<>();
        fruitEntries = new ArrayList<>(map.entrySet());
        orderedFruitMap = new LinkedHashMap<>();
        // Create a tempMap to store keys for later lookup
        final Map<Apple, Integer> tempMap = new HashMap<>();
        for (final Entry<Integer, Apple> e : fruitEntries) {
            fruitlist.add(e.getValue());
            tempMap.put(e.getValue(), e.getKey());
        }
        Collections.sort(fruitlist);
        for (final Apple fruit : fruitlist) {
            orderedFruitMap.put(tempMap.get(fruit), fruit);
        }
        return orderedFruitMap;
    }

    public Map<Student, String> sortStudentMapByIds(final Map<Student, String> stMap) {
        final List<Student> stList = new ArrayList<Student>(stMap.keySet());
        Collections.sort(stList, new IdComparator());
        final Map<Student, String> studentLinkedMap = new LinkedHashMap<Student, String>();
        for (final Student s : stList) {
            studentLinkedMap.put(s, stMap.get(s));
        }
        return studentLinkedMap;
    }
}
