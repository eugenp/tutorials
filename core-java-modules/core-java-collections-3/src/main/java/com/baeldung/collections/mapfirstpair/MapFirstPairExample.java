package com.baeldung.collections.mapfirstpair;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapFirstPairExample {

    public Map.Entry<Integer, String> getFirstPairUsingIterator(Map<Integer, String> map) {
        if (map == null || map.size() == 0)
            return null;

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet()
            .iterator();

        if (iterator.hasNext())
            return iterator.next();

        return null;
    }

    public Map.Entry<Integer, String> getFirstPairUsingStream(Map<Integer, String> map) {
        if (map == null || map.size() == 0)
            return null;

        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();

        return entrySet.stream()
            .findFirst()
            .get();
    }

    public Map<Integer, String> populateMapValues(Map<Integer, String> map) {
        if (map != null) {
            map.put(5, "A");
            map.put(1, "B");
            map.put(2, "C");
        }
        return map;
    }

}