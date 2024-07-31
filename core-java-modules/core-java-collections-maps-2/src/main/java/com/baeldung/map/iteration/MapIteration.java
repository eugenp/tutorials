package com.baeldung.map.iteration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapIteration {

    public static void main(String[] args) {
        MapIteration mapIteration = new MapIteration();
        Map<String, Integer> map = new HashMap<>();

        map.put("One", 1);
        map.put("Three", 3);
        map.put("Two", 2);

        System.out.println("Iterating Keys of Map Using KeySet");
        mapIteration.iterateKeys(map);

        System.out.println("Iterating Values of Map Using values()");
        mapIteration.iterateValues(map);

        System.out.println("Iterating Map Using Entry Set");
        mapIteration.iterateUsingEntrySet(map);

        System.out.println("Iterating Using Iterator and Map Entry");
        mapIteration.iterateUsingIteratorAndEntry(map);

        System.out.println("Iterating Using Iterator and KeySet");
        mapIteration.iterateUsingIteratorAndKeySet(map);

        System.out.println("Iterating values Using Iterator and values()");
        mapIteration.iterateUsingIteratorAndValues(map);

        System.out.println("Iterating Using KeySet and For Each");
        mapIteration.iterateUsingKeySetAndForeach(map);

        System.out.println("Iterating Map Using Lambda Expression");
        mapIteration.iterateUsingLambda(map);

        System.out.println("Iterating Map By Keys Using Lambda Expression");
        mapIteration.iterateByKeysUsingLambda(map);

        System.out.println("Iterating values Using Lambda Expression");
        mapIteration.iterateValuesUsingLambda(map);

        System.out.println("Iterating Using Stream API");
        mapIteration.iterateUsingStreamAPI(map);
    }

    public void iterateUsingIteratorAndValues(Map<String, Integer> map) {
        Iterator<Integer> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            System.out.println("value :" + value);
        }
    }

    public void iterateUsingEntrySet(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public void iterateUsingLambda(Map<String, Integer> map) {
        map.forEach((k, v) -> System.out.println((k + ":" + v)));
    }

    public void iterateByKeysUsingLambda(Map<String, Integer> map) {
        map.keySet().forEach(k -> System.out.println((k + ":" + map.get(k))));
    }

    public void iterateValuesUsingLambda(Map<String, Integer> map) {
        map.values().forEach(v -> System.out.println(("value: " + v)));
    }

    public void iterateUsingIteratorAndEntry(Map<String, Integer> map) {
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pair = iterator.next();
            System.out.println(pair.getKey() + ":" + pair.getValue());
        }
    }

    public void iterateUsingIteratorAndKeySet(Map<String, Integer> map) {
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ":" + map.get(key));
        }
    }

    public void iterateUsingKeySetAndForeach(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }

    public void iterateUsingStreamAPI(Map<String, Integer> map) {
        map.entrySet()
            .stream()
            .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
    }

    public void iterateKeys(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key);
        }
    }

    public void iterateValues(Map<String, Integer> map) {
        for (Integer value : map.values()) {
            System.out.println(value);
        }
    }

}