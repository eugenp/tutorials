package com.baeldung.article;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author AshwiniKeshri
 *
 */
public class HashMapExample {
    public static void main(String[] args) {

        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(5, "A");
        hashmap.put(1, "B");
        hashmap.put(2, "C");
        // hashmap.put(0, "D");

        System.out.println(hashmap);

        Set<Map.Entry<Integer, String>> entrySet = hashmap.entrySet();

        Iterator<Map.Entry<Integer, String>> iterator = entrySet.iterator();

        if (iterator.hasNext())
            System.out.println("Using Iteraor: " + iterator.next());

        System.out.println("Using Stream: " + entrySet.stream()
            .findFirst()
            .orElse(new AbstractMap.SimpleEntry<Integer, String>(-1, "DEFAULT")));

    }
}
