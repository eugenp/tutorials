package com.baeldung.map.hashmap.entryremoval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class HashMapToArrayList {
    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        hashMap.put("Apple", 1);
        hashMap.put("Banana", 2);
        hashMap.put("Orange", 3);

        ArrayList<String> keysList1 = getArrayListByHashMapKeys(hashMap.keySet());
        ArrayList<Integer> valuesList1 = getArrayListByHashMapValues(hashMap.values());

        System.out.println("Keys ArrayList1: " + keysList1);
        System.out.println("Values ArrayList1: " + valuesList1);

        // Using forEach method
        ArrayList<String> keysList2 = new ArrayList<>();
        ArrayList<Integer> valuesList2 = new ArrayList<>();

        hashMap.forEach((key, value) -> {
            keysList2.add(key);
            valuesList2.add(value);
        });

        System.out.println("Keys ArrayList2: " + keysList2);
        System.out.println("Values ArrayList2: " + valuesList2);
    }

    public static ArrayList<String> getArrayListByHashMapKeys(Set<String> hashMapKeys) {
        ArrayList<String> keysList = new ArrayList<>(hashMapKeys);
        return keysList;
    }

    public static ArrayList<Integer> getArrayListByHashMapValues(Collection<Integer> hashMapValues) {
        ArrayList<Integer> valuesList = new ArrayList<>(hashMapValues);
        return valuesList;
    }
}
