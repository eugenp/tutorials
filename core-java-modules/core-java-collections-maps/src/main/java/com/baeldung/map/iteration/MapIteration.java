package com.baeldung.map.iteration;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.eclipse.collections.api.map.MutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapIteration {

    private final Logger LOGGER = LoggerFactory.getLogger(MapIteration.class);

    public long iterateUsingIteratorAndValues(Map<Integer, Integer> map) {
        long sum = 0;
        Iterator<Integer> iterator = map.values()
            .iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            sum += value;
        }
        return sum;
    }

    public long iterateUsingEnhancedForLoopAndEntrySet(Map<Integer, Integer> map) {
        long sum = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public void iterateUsingLambdaAndForEach(Map<Integer, Integer> map) {
        map.forEach((k, v) -> LOGGER.info("Key: {}, Value: {}", k, v));
    }

    public long iterateByKeysUsingLambdaAndForEach(Map<Integer, Integer> map) {
        AtomicLong sum = new AtomicLong(0);
        map.keySet()
            .forEach(k -> sum.addAndGet(map.get(k)));
        return sum.get();
    }

    public long iterateValuesUsingLambdaAndForEach(Map<Integer, Integer> map) {
        AtomicLong sum = new AtomicLong(0);
        map.values()
            .forEach(v -> sum.addAndGet(v));
        return sum.get();
    }

    public long iterateUsingIteratorAndEntrySet(Map<Integer, Integer> map) {
        long sum = 0;
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> pair = iterator.next();
            sum += pair.getValue();
        }
        return sum;
    }

    public long iterateUsingIteratorAndKeySet(Map<Integer, Integer> map) {
        long sum = 0;
        Iterator<Integer> iterator = map.keySet()
            .iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            sum += map.get(key);
        }
        return sum;
    }

    public long iterateUsingKeySetAndEnhanceForLoop(Map<Integer, Integer> map) {
        long sum = 0;
        for (Integer key : map.keySet()) {
            sum += map.get(key);
        }
        return sum;
    }

    public long iterateUsingStreamAPIAndEntrySet(Map<Integer, Integer> map) {
        return map.entrySet()
            .stream()
            .mapToLong(Map.Entry::getValue)
            .sum();

    }

    public long iterateUsingStreamAPIAndKeySet(Map<Integer, Integer> map) {
        return map.keySet()
            .stream()
            .mapToLong(map::get)
            .sum();
    }

    public long iterateKeysUsingKeySetAndEnhanceForLoop(Map<Integer, Integer> map) {
        long sum = 0;
        for (Integer key : map.keySet()) {
            sum += map.get(key);
        }
        return sum;
    }

    public long iterateValuesUsingValuesMethodAndEnhanceForLoop(Map<Integer, Integer> map) {
        long sum = 0;
        for (Integer value : map.values()) {
            sum += value;
        }
        return sum;
    }

    public long iterateUsingMapIteratorApacheCollection(IterableMap<Integer, Integer> map) {
        long sum = 0;
        MapIterator<Integer, Integer> iterate = map.mapIterator();
        while (iterate.hasNext()) {
            iterate.next();
            sum += iterate.getValue();
        }
        return sum;
    }

    public long iterateEclipseMap(MutableMap<Integer, Integer> mutableMap) throws IOException {
        AtomicLong sum = new AtomicLong(0);
        mutableMap.forEachKeyValue((key, value) -> {
            sum.addAndGet(value);
        });
        return sum.get();
    }

    public long iterateMapUsingParallelStreamApi(Map<Integer, Integer> map) throws IOException {
        return map.entrySet()
            .parallelStream()
            .mapToLong(Map.Entry::getValue)
            .sum();
    }

}