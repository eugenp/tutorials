package com.baeldung.map.hashmap.convertor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class HashMapToArrayListUnitTest {

    @Test
    public void whenCreateArrayListsUsingConstructor_thenCompareSuccess() {
        HashMap<String, Integer> hashMap = new HashMap<>();

        hashMap.put("Apple", 1);
        hashMap.put("Banana", 2);
        hashMap.put("Orange", 3);

        ArrayList<String> arrayListKeys = new ArrayList<>(hashMap.keySet());
        ArrayList<Integer> arrayListValues = new ArrayList<>(hashMap.values());

        assertTrue(hashMap.keySet().containsAll(arrayListKeys));
        assertTrue(arrayListKeys.containsAll(hashMap.keySet()));
        assertTrue(hashMap.values().containsAll(arrayListValues));
        assertTrue(arrayListValues.containsAll(hashMap.values()));
    }

    @Test
    public void whenCreateArrayListsUsingForEachLoop_thenCompareSuccess() {
        HashMap<String, Integer> hashMap = new HashMap<>();

        hashMap.put("Apple", 1);
        hashMap.put("Banana", 2);
        hashMap.put("Orange", 3);

        ArrayList<String> arrayListKeys = new ArrayList<>();
        ArrayList<Integer> arrayListValues = new ArrayList<>();

        hashMap.forEach((key, value) -> {
            arrayListKeys.add(key);
            arrayListValues.add(value);
        });

        assertTrue(hashMap.keySet().containsAll(arrayListKeys));
        assertTrue(arrayListKeys.containsAll(hashMap.keySet()));
        assertTrue(hashMap.values().containsAll(arrayListValues));
        assertTrue(arrayListValues.containsAll(hashMap.values()));
    }
}
