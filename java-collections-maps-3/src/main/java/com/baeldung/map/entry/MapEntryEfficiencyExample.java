package com.baeldung.map.entry;

import java.util.HashMap;
import java.util.Map;

public class MapEntryEfficiencyExample {

    public static void main(String[] args) {
        MapEntryEfficiencyExample mapEntryEfficiencyExample = new MapEntryEfficiencyExample();
        Map<String, String> map = new HashMap<>();

        map.put("Robert C. Martin", "Clean Code");
        map.put("Joshua Bloch", "Effective Java");

        System.out.println("Iterating Using Map.KeySet - 2 operations");
        mapEntryEfficiencyExample.usingKeySet(map);

        System.out.println("Iterating Using Map.Entry - 1 operation");
        mapEntryEfficiencyExample.usingEntrySet(map);

    }

    public void usingKeySet(Map<String, String> bookMap) {
        for (String key : bookMap.keySet()) {
            System.out.println("key: " + key + " value: " + bookMap.get(key));
        }
    }

    public void usingEntrySet(Map<String, String> bookMap) {
        for (Map.Entry<String, String> book: bookMap.entrySet()) {
            System.out.println("key: " + book.getKey() + " value: " + book.getValue());
        }
    }
}
