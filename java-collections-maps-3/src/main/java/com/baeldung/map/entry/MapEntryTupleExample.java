package com.baeldung.map.entry;

import java.util.*;

public class MapEntryTupleExample {

    public static void main(String[] args) {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        entries.add(new AbstractMap.SimpleEntry<>("Joshua Bloch", "Effective Java"));
        entries.add(new AbstractMap.SimpleEntry<>("Robert C Martin", "Clean Code"));
        entries.add(new AbstractMap.SimpleEntry<>("Robert C Martin", "Clean Architecture"));

        for (Map.Entry entry : entries) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }
    }
}
