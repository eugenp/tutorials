package com.baeldung.convertlisttomap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Test;

public class ListToMapUnitTest {

    @Test
    public void givenAList_whenConvertWithJava8GroupBy_thenReturnMap() {
        List<String> strings = Arrays.asList("List", "Map", "Set", "Tree");

        Map<Integer, List<String>> convertedMap = new HashMap<>();

        Supplier<List<String>> listSupplier = () -> {
            return new ArrayList<>();
        };

        Supplier<Map<Integer, List<String>>> mapFactory = () -> {
            return new HashMap<>();
        };
        convertedMap = strings.stream()
            .collect(Collectors.groupingBy(String::length, mapFactory, Collectors.toCollection(listSupplier)));

        assertEquals(2, convertedMap.size());
        assertTrue(convertedMap.get(3)
            .contains("Map"));
    }

    @Test
    public void givenAList_whenConvertWithJava8Collect_thenReturnMap() {
        List<String> strings = Arrays.asList("List", "Map", "Set", "Tree");

        Map<Integer, List<String>> convertedMap = new HashMap<>();

        Supplier<Map<Integer, List<String>>> mapFactory = () -> {
            return new HashMap<>();
        };

        Supplier<List<String>> listSupplier = () -> {
            return new ArrayList<>();
        };

        BiConsumer<Map<Integer, List<String>>, String> accumulator = (response, element) -> {
            Integer key = element.length();
            List<String> values = response.getOrDefault(key, listSupplier.get());
            values.add(element);
            response.put(key, values);
        };

        BiConsumer<Map<Integer, List<String>>, Map<Integer, List<String>>> combiner = (res1, res2) -> {
            res1.putAll(res2);
        };

        convertedMap = strings.stream()
            .collect(mapFactory, accumulator, combiner);

        assertEquals(2, convertedMap.size());
        assertTrue(convertedMap.get(3)
            .contains("Map"));
    }

    @Test
    public void givenAList_whenConvertWithCollectorToMap_thenReturnMap() {
        List<String> strings = Arrays.asList("List", "Map", "Set", "Tree");

        Map<Integer, List<String>> convertedMap = new HashMap<>();

        Supplier<Map<Integer, List<String>>> mapFactory = () -> {
            return new HashMap<>();
        };

        Supplier<List<String>> listSupplier = () -> {
            return new ArrayList<>();
        };

        convertedMap = strings.stream()
            .collect(Collectors.toMap(String::length, (p) -> {
                List<String> strs = listSupplier.get();
                strs.add(p);
                return strs;
            }, (existing, replacement) -> {
                existing.addAll(replacement);
                return existing;
            }, mapFactory));

        assertEquals(2, convertedMap.size());
        assertTrue(convertedMap.get(3)
            .contains("Map"));
    }

}
