package org.baeldung.java.sorting;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class MapSort {

    @Test
    public void givenMap_whenSortingByKeys_thenSortedMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(55, "John");
        map.put(22, "Apple");
        map.put(66, "Earl");
        map.put(77, "Pearl");
        map.put(12, "George");
        map.put(6, "Rocky");
        Integer[] sortedKeys = new Integer[] { 6, 12, 22, 55, 66, 77 };

        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Entry<Integer, String>>() {
            @Override
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        HashMap<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        assertTrue(Arrays.equals(sortedMap.keySet().toArray(), sortedKeys));
    }

    @Test
    public void givenMap_whenSortingByValues_thenSortedMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(55, "John");
        map.put(22, "Apple");
        map.put(66, "Earl");
        map.put(77, "Pearl");
        map.put(12, "George");
        map.put(6, "Rocky");
        String[] sortedValues = new String[] 
          { "Apple", "Earl", "George", "John", "Pearl", "Rocky" };

        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Entry<Integer, String>>() {
            @Override
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        assertTrue(Arrays.equals(sortedMap.values().toArray(), sortedValues));
    }



    // public void showMap(Map<Integer, String> map1) {
    // for (Map.Entry<Integer, String> entry : map1.entrySet()) {
    // System.out.println("[Key: " + entry.getKey() + " , " + "Value: " + entry.getValue() + "] ");
    //
    // }
    //
    // }

}
