package com.baeldung.map.iteration;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.eclipse.collections.api.map.MutableMap;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class MapIteration {

    public long iterateUsingIteratorAndValues(Map<Integer, Integer> map) {
        long sum = 0;
        Iterator<Integer> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            sum += value;
        }
        return sum;
    }

    public long iterateUsingEntrySet(Map<Integer, Integer> map) {
        long sum = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            sum += entry.getKey() + entry.getValue();
        }
        return sum;
    }

    public long iterateUsingLambda(Map<Integer, Integer> map) {
        final long[] sum = { 0 };
        map.forEach((k, v) -> sum[0] += k + v);
        return sum[0];
    }

    public long iterateByKeysUsingLambda(Map<Integer, Integer> map) {
        final long[] sum = { 0 };
        map.keySet()
            .forEach(k -> sum[0] += k + map.get(k));

        return sum[0];
    }

    public long iterateValuesUsingLambda(Map<Integer, Integer> map) {
        final long[] sum = { 0 };
        map.values()
            .forEach(v -> sum[0] += v);
        return sum[0];
    }

    public long iterateUsingIteratorAndEntry(Map<Integer, Integer> map) {
        long sum = 0;
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> pair = iterator.next();
            sum += pair.getKey() + pair.getValue();
        }
        return sum;
    }

    public long iterateUsingIteratorAndKeySet(Map<Integer, Integer> map) {
        long sum = 0;
        Iterator<Integer> iterator = map.keySet()
            .iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            sum += key + map.get(key);
        }
        return sum;
    }

    public long iterateUsingKeySetAndForeach(Map<Integer, Integer> map) {
        long sum = 0;
        for (Integer key : map.keySet()) {
            sum += key + map.get(key);
        }
        return sum;
    }

    public long iterateUsingStreamAPIAndEntrySet(Map<Integer, Integer> map) {
        final long[] sum = { 0 };
        map.entrySet()
            .stream()
            .forEach(e -> sum[0] += e.getKey() + e.getValue());
        return sum[0];
    }

    public long iterateUsingStreamAPIAndKeySet(Map<Integer, Integer> map) {
        final long[] sum = { 0 };
        map.keySet()
            .stream()
            .forEach(e -> sum[0] += map.get(e));
        return sum[0];
    }

    public long iterateKeys(Map<Integer, Integer> map) {
        long sum = 0;
        for (Integer key : map.keySet()) {
            sum += key;
        }
        return sum;
    }

    public long iterateValues(Map<Integer, Integer> map) {
        long sum = 0;
        for (Integer value : map.values()) {
            sum += value ;
        }
        return sum;
    }

    public long iterateUsingMapIteratorApacheCollection(IterableMap<Integer, Integer> map) {
        long sum = 0;
        MapIterator<Integer, Integer> iterate = map.mapIterator();
        while (iterate.hasNext()) {
            sum += iterate.next() + iterate.getValue();
        }
        return sum;
    }

    public long iterateEclipseMap(MutableMap<Integer, Integer> mutableMap) throws IOException {
        final long[] sum = { 0 };
        mutableMap.forEachKeyValue((key, value) -> {
            sum[0] += key + value;
        });
        return sum[0];
    }


    public long iterateMapUsingParallelStreamApiParallel(Map<Integer, Integer> map) throws IOException {
        return map.entrySet()
            .parallelStream()
            .mapToLong(e -> e.getKey() + e.getValue())
            .sum();
    }

}