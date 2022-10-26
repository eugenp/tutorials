package com.baeldung.map.multivaluedmap;

import java.util.HashMap;
import java.util.Map;

// Example class for Map.
public class MapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        // Putting key-value pairs into our map.
        map.put("first", 1);
        map.put(null, 2);
        map.put("third", null);
        
        // Printing values for each key.
        System.out.println("Value for key 'first': " + map.get("first"));
        System.out.println("Value for key 'null': " + map.get(null));
        System.out.println("Value for key 'third': " + map.get("third"));
    }
}
