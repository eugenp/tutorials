package com.baeldung.hashmaptoarraylist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;

public class HashMapToArrayListConverterUtils {

    static ArrayList<String> convertUsingConstructor(HashMap<Integer, String> hashMap) {
        if (hashMap == null) {
            return null;
        }
        return new ArrayList<String>(hashMap.values());
    }

    static ArrayList<String> convertUsingAddAllMethod(HashMap<Integer, String> hashMap) {
        if (hashMap == null) {
            return null;
        }

        ArrayList<String> arrayList = new ArrayList<String>(hashMap.size());
        arrayList.addAll(hashMap.values());

        return arrayList;
    }

    static ArrayList<String> convertUsingStreamApi(HashMap<Integer, String> hashMap) {
        if (hashMap == null) {
            return null;
        }

        return hashMap.values()
            .stream()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    static ArrayList<String> convertUsingForLoop(HashMap<Integer, String> hashMap) {
        if (hashMap == null) {
            return null;
        }

        ArrayList<String> arrayList = new ArrayList<String>(hashMap.size());
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            arrayList.add(entry.getValue());
        }

        return arrayList;
    }

    static public ArrayList<String> convertUsingGuava(HashMap<Integer, String> hashMap) {
        if (hashMap == null) {
            return null;
        }

        EntryTransformer<Integer, String, String> entryMapTransformer = (key, value) -> value;

        return Lists.newArrayList(Maps.transformEntries(hashMap, entryMapTransformer)
            .values());
    }

}
