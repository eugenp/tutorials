package com.baeldung.streamtomapandmultimap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StreamToMapAndMultiMapUnitTest {
    @Test
    public void givenStringStream_whenConvertingToMapWithMerge_thenExpectedMapIsGenerated() {
        Stream<String> stringStream = Stream.of("one", "two", "three", "two");

        Map<String, String> mergedMap = stringStream.collect(
                Collectors.toMap(s -> s, s -> s, (s1, s2) -> s1 + ", " + s2)
        );

        // Define the expected map
        Map<String, String> expectedMap = Map.of(
                "one", "one",
                "two", "two, two",
                "three", "three"
        );

        assertEquals(expectedMap, mergedMap);
    }

    @Test
    public void givenStringStream_whenConvertingToMultimap_thenExpectedMultimapIsGenerated() {
        Stream<String> stringStream = Stream.of("one", "two", "three", "two");

        ListMultimap<String, String> multimap = stringStream.collect(
                ArrayListMultimap::create,
                (map, element) -> map.put(element, element),
                ArrayListMultimap::putAll
        );

        ListMultimap<String, String> expectedMultimap = ArrayListMultimap.create();
        expectedMultimap.put("one", "one");
        expectedMultimap.put("two", "two");
        expectedMultimap.put("two", "two");
        expectedMultimap.put("three", "three");

        assertEquals(expectedMultimap, multimap);
    }

    @Test
    public void givenStringStream_whenConvertingToMultimapWithStreamReduce_thenExpectedMultimapIsGenerated() {
        Stream<String> stringStream = Stream.of("one", "two", "three", "two");

        Map<String, List<String>> multimap = stringStream.reduce(
                new HashMap<>(),
                (map, element) -> {
                    map.computeIfAbsent(element, k -> new ArrayList<>()).add(element);
                    return map;
                },
                (map1, map2) -> {
                    map2.forEach((key, value) -> map1.merge(key, value, (list1, list2) -> {
                        list1.addAll(list2);
                        return list1;
                    }));
                    return map1;
                }
        );

        Map<String, List<String>> expectedMultimap = new HashMap<>();
        expectedMultimap.put("one", Collections.singletonList("one"));
        expectedMultimap.put("two", Arrays.asList("two", "two"));
        expectedMultimap.put("three", Collections.singletonList("three"));

        assertEquals(expectedMultimap, multimap);
    }

    @Test
    public void givenStringStream_whenConvertingToMapWithStreamReduce_thenExpectedMapIsGenerated() {
        Stream<String> stringStream = Stream.of("one", "two", "three", "two");

        Map<String, String> resultMap = stringStream.reduce(
                new HashMap<>(),
                (map, element) -> {
                    map.put(element, element);
                    return map;
                },
                (map1, map2) -> {
                    map1.putAll(map2);
                    return map1;
                }
        );

        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("one", "one");
        expectedMap.put("two", "two");
        expectedMap.put("three", "three");

        assertEquals(expectedMap, resultMap);
    }
}
