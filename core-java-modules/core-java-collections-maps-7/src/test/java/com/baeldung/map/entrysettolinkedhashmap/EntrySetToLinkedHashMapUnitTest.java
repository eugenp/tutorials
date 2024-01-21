package com.baeldung.map.entrysettolinkedhashmap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntrySetToLinkedHashMapUnitTest {

    private Map<Integer, String> map;

    @BeforeEach
    public void init() {
        map = Map.of(1, "value 1", 2, "value 2");
    }

    @Test
    public void givenMap_thenConvertToLinkedHashMap() {
        Map<Integer, String> result = new LinkedHashMap<>(map);

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }

    @Test
    public void givenMap_whenUsingCollectorGroupingBy_thenCollectToLinkedHashMap() {
        Map<String, Set<String>> countryToCities = Map.of("Paris", "France", "Nice", "France", "Madrid", "Spain")
            .entrySet()
            .stream()
            .collect(Collectors.groupingBy(Map.Entry::getValue, LinkedHashMap::new, Collectors.mapping(Map.Entry::getKey, Collectors.toSet())));

        assertThat(countryToCities).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry("France", Set.of("Paris", "Nice")), entry("Spain", Set.of("Madrid")));
    }

    @Test
    public void givenMap_whenUsingCollectorToMap_thenCollectToHashMapAndConvert() {
        Map<Integer, String> result = new LinkedHashMap<>(map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }

    @Test
    public void givenMap_whenUsingCollectorToMap_thenCollectToLinkedHashMap() {
        Map<Integer, String> result = map.entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> {
                    throw new RuntimeException();
                }, 
                LinkedHashMap::new));

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }

    @Test
    public void givenMap_whenUsingPutOnIteration_thenCollectToLinkedHashMap() {
        Map<Integer, String> result = new LinkedHashMap<>();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }

    @Test
    public void givenMap_whenUsingPutWithMapForEach_thenCollectToLinkedHashMap() {
        Map<Integer, String> result = new LinkedHashMap<>();

        map.forEach((k, v) -> result.put(k, v));

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }

    @Test
    public void givenMap_whenUsingPutWithSetForEach_thenCollectToLinkedHashMap() {
        Map<Integer, String> result = new LinkedHashMap<>();

        map.entrySet()
            .forEach(entry -> result.put(entry.getKey(), entry.getValue()));

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }

    @Test
    public void givenMap_whenUsingPutWithStreamForEach_thenCollectToLinkedHashMap() {
        Map<Integer, String> result = new LinkedHashMap<>();

        map.entrySet()
            .stream()
            .forEach(entry -> result.put(entry.getKey(), entry.getValue()));

        assertThat(result).isExactlyInstanceOf(LinkedHashMap.class)
            .containsOnly(entry(1, "value 1"), entry(2, "value 2"));
    }
}