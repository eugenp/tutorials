package com.baeldung.map.entry;

import java.util.HashMap;
import java.util.Map;

public class MapEntryEfficiencyExample {

    public static void main(String[] args) {
        MapEntryEfficiencyExample mapEntryEfficiencyExample = new MapEntryEfficiencyExample();
        Map<Integer, String> bookMap = new HashMap<>();
        bookMap.put(1, "Clean Code");
        bookMap.put(2, "Effective Java");

        System.out.println("Inefficient Method - Iterating Using Map.KeySet");
        mapEntryEfficiencyExample.inefficientMethodUsingKeySet(bookMap);

        System.out.println("More Efficient Method - Iterating Using Map.Entry");
        mapEntryEfficiencyExample.efficientMethodUsingMapEntry(bookMap);

    }

    public void inefficientMethodUsingKeySet(Map<Integer, String> bookMap) {
        for (Integer key : bookMap.keySet()) {
            System.out.println("key: " + key + " value: " + bookMap.get(key));
        }
    }

    public void efficientMethodUsingMapEntry(Map<Integer, String> bookMap) {
        for (Map.Entry<Integer, String> book: bookMap.entrySet()) {
            System.out.println("key: " + book.getKey() + " value: " + book.getValue());
        }
    }
}
