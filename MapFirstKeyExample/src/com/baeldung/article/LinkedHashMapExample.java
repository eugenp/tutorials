package com.baeldung.article;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author AshwiniKeshri
 *
 */
public class LinkedHashMapExample {

    public static void main(String[] args) {

        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(5, "A");
        linkedHashMap.put(1, "B");
        linkedHashMap.put(2, "C");
        // linkedHashMap.put(0, "D");

        System.out.println(linkedHashMap);

        Set<Map.Entry<Integer, String>> entrySet = linkedHashMap.entrySet();

        Iterator<Map.Entry<Integer, String>> iterator = entrySet.iterator();

        if (iterator.hasNext())
            System.out.println("Using Iterator: " + iterator.next());

        System.out.println("Using Stream: " + entrySet.stream()
            .findFirst()
            .orElse(new AbstractMap.SimpleEntry<Integer, String>(0, "DEFAULT")));
    }

}
