package com.baeldung.map.mapmax;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class MapMax {

    public <K, V extends Comparable<V>> V maxUsingIteration(Map<K, V> map) {

        Map.Entry<K, V> maxEntry = null;

        for (Map.Entry<K, V> entry : map.entrySet()) {

            if (maxEntry == null || entry.getValue()
                .compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry.getValue();
    }

    public <K, V extends Comparable<V>> V maxUsingCollectionsMax(Map<K, V> map) {

        Entry<K, V> maxEntry = Collections.max(map.entrySet(), new Comparator<Entry<K, V>>() {
            public int compare(Entry<K, V> e1, Entry<K, V> e2) {
                return e1.getValue()
                    .compareTo(e2.getValue());
            }
        });

        return maxEntry.getValue();
    }

    public <K, V extends Comparable<V>> V maxUsingCollectionsMaxAndLambda(Map<K, V> map) {

        Entry<K, V> maxEntry = Collections.max(map.entrySet(), Entry.comparingByValue());

        return maxEntry.getValue();
    }

    public <K, V extends Comparable<V>> V maxUsingCollectionsMaxAndMethodReference(Map<K, V> map) {

        Entry<K, V> maxEntry = Collections.max(map.entrySet(), Entry.comparingByValue());

        return maxEntry.getValue();
    }

    public <K, V extends Comparable<V>> V maxUsingStreamAndLambda(Map<K, V> map) {

        Optional<Entry<K, V>> maxEntry = map.entrySet()
            .stream()
            .max(Entry.comparingByValue());

        return maxEntry.get()
            .getValue();
    }

    public <K, V extends Comparable<V>> V maxUsingStreamAndMethodReference(Map<K, V> map) {

        Optional<Entry<K, V>> maxEntry = map.entrySet()
            .stream()
            .max(Entry.comparingByValue());

        return maxEntry.get()
            .getValue();
    }

    public static void main(String[] args) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        map.put(1, 3);
        map.put(2, 4);
        map.put(3, 5);
        map.put(4, 6);
        map.put(5, 7);
        
        MapMax mapMax = new MapMax();

        System.out.println(mapMax.maxUsingIteration(map));
        System.out.println(mapMax.maxUsingCollectionsMax(map));
        System.out.println(mapMax.maxUsingCollectionsMaxAndLambda(map));
        System.out.println(mapMax.maxUsingCollectionsMaxAndMethodReference(map));
        System.out.println(mapMax.maxUsingStreamAndLambda(map));
        System.out.println(mapMax.maxUsingStreamAndMethodReference(map));

    }

}
