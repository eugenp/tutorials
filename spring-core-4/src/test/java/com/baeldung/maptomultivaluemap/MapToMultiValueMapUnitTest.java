package com.baeldung.maptomultivaluemap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapToMultiValueMapUnitTest {

    @Test
    public void givenMap_whenConvertToMultiValueMapUsingIteration_thenCountEntries() {

        Map<String, List<String>> map = new HashMap<>();
        map.put("rollNo", Arrays.asList("4", "2", "7", "3"));
        map.put("name", Arrays.asList("John", "Alex", "Maria", "Jack"));
        map.put("hobbies", Arrays.asList("Badminton", "Reading novels", "Painting", "Cycling"));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            multiValueMap.put(entry.getKey(), entry.getValue());
        }

        assertEquals(3, multiValueMap.size());
    }

    @Test
    public void givenMap_whenConvertToMultiValueMapUsingCollections_thenCountEntries() {

        Map<String, List<String>> map = new HashMap<>();
        map.put("rollNo", Arrays.asList("4", "2", "7", "3"));
        map.put("name", Arrays.asList("John", "Alex", "Maria", "Jack"));
        map.put("hobbies", Arrays.asList("Badminton", "Reading novels", "Painting", "Cycling"));

        MultiValueMap<String, String> multiValueMap = CollectionUtils.toMultiValueMap(map);

        assertEquals(3, multiValueMap.size());
    }

    @Test
    public void givenMap_whenConvertToMultiValueMapUsingStreams_thenCountEntries() {

        Map<String, List<String>> map = new HashMap<>();
        map.put("rollNo", Arrays.asList("4", "2", "7", "3"));
        map.put("name", Arrays.asList("John", "Alex", "Maria", "Jack"));
        map.put("hobbies", Arrays.asList("Badminton", "Reading novels", "Painting", "Cycling"));

        MultiValueMap<String, String> multiValueMap = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedMultiValueMap::new));

        assertEquals(3, multiValueMap.size());
    }
}
