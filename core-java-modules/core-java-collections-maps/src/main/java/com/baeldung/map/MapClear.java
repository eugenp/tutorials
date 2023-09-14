package com.baeldung.map;

import java.util.HashMap;
import java.util.Map;

public class MapClear {
    public static Map returnCopyAndClearMap() {
        // Create a HashMap
        Map<String, Integer> scores = new HashMap<>();
        Map<String, Integer> scores_copy;

        // Add some key-value pairs
        scores.put("Alice", 90);
        scores.put("Bob", 85);
        scores.put("Charlie", 95);

        scores_copy = scores;

        System.out.println("Before clearing: " + scores);

        // Clear the map
        scores.clear();

        System.out.println("After clearing: " + scores);
        return scores_copy;
    }

    public static Map returnCopyAndRewriteMap() {
        // Create a HashMap
        Map<String, Integer> scores = new HashMap<>();
        Map<String, Integer> scores_copy;

        // Add some key-value pairs
        scores.put("Alice", 90);
        scores.put("Bob", 85);
        scores.put("Charlie", 95);

        scores_copy = scores;

        System.out.println("Before clearing: " + scores);

        // Create a new map
        scores = new HashMap<>();

        System.out.println("After clearing: " + scores);

        return scores_copy;
    }
}