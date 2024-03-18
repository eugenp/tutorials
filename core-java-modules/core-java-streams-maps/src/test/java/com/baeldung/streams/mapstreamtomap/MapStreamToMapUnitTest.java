package com.baeldung.streams.mapstreamtomap;

import static java.lang.Math.max;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class MapStreamToMapUnitTest {

    Map<String, Integer> playerMap1 = new HashMap<String, Integer>() {{
        put("Kai", 92);
        put("Liam", 100);
    }};
    Map<String, Integer> playerMap2 = new HashMap<String, Integer>() {{
        put("Eric", 42);
        put("Kevin", 77);
    }};
    Map<String, Integer> playerMap3 = new HashMap<String, Integer>() {{
        put("Saajan", 35);
    }};
    Map<String, Integer> playerMap4 = new HashMap<String, Integer>() {{
        put("Kai", 76);
    }};
    Map<String, Integer> playerMap5 = new HashMap<String, Integer>() {{
        put("Kai", null);
        put("Jerry", null);
    }};

    static Stream<Map<String, Integer>> getMapStream(Map<String, Integer>... playerMaps) {
        List<Map<String, Integer>> theList = Lists.newArrayList(playerMaps);
        Collections.shuffle(theList);
        return theList.stream();
    }

    @Test
    void givenMapsStream_whenUsingFlatMapAndToMap_thenMultipleMapsMergedIntoOneMap() {

        Map<String, Integer> expectedMap = new HashMap<String, Integer>() {{
            put("Saajan", 35);
            put("Liam", 100);
            put("Kai", 92);
            put("Eric", 42);
            put("Kevin", 77);
        }};

        Map<String, Integer> mergedMap = getMapStream(playerMap1, playerMap2, playerMap3).flatMap(map -> map.entrySet()
                .stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        assertEquals(expectedMap, mergedMap);
    }

    @Test
    void givenMapsWithDuplicateKeys_whenUsingFlatMapAndToMap_thenMultipleMapsMergedIntoOneMap() {

        Map<String, Integer> expectedMap = new HashMap<String, Integer>() {{
            put("Saajan", 35);
            put("Liam", 100);
            put("Kai", 92); // max of 76 and 92
            put("Eric", 42);
            put("Kevin", 77);
        }};

        assertThrows(IllegalStateException.class, () -> getMapStream(playerMap1, playerMap2, playerMap3, playerMap4).flatMap(map -> map.entrySet()
                .stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), "Duplicate key Kai (attempted merging values 92 and 76)");

        Map<String, Integer> mergedMap = getMapStream(playerMap1, playerMap2, playerMap3, playerMap4).flatMap(map -> map.entrySet()
                .stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::max));

        assertEquals(expectedMap, mergedMap);
    }

    private Integer maxInteger(Integer int1, Integer int2) {
        if (int1 == null) {
            return int2;
        }
        if (int2 == null) {
            return int1;
        }
        return max(int1, int2);
    }

    @Test
    void givenMapsWithDuplicateKeysAndNullValues_whenUsingFlatMapWithForEach_thenMultipleMapsMergedIntoOneMap() {

        Map<String, Integer> expectedMap = new HashMap<String, Integer>() {{
            put("Saajan", 35);
            put("Liam", 100);
            put("Kai", 92); // max of 92, 76, and null
            put("Eric", 42);
            put("Kevin", 77);
            put("Jerry", null);
        }};

        assertThrows(NullPointerException.class, () -> getMapStream(playerMap1, playerMap2, playerMap3, playerMap4, playerMap5).flatMap(map -> map.entrySet()
                .stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::max)));

        Map<String, Integer> mergedMap = new HashMap<>();
        getMapStream(playerMap1, playerMap2, playerMap3, playerMap4, playerMap5).flatMap(map -> map.entrySet()
                .stream())
            .forEach(entry -> {
                String k = entry.getKey();
                Integer v = entry.getValue();
                if (mergedMap.containsKey(k)) {
                    mergedMap.put(k, maxInteger(mergedMap.get(k), v));
                } else {
                    mergedMap.put(k, v);
                }
            });
        assertEquals(expectedMap, mergedMap);

    }

    @Test
    void givenMapsWithDuplicateKeysAndNullValues_whenUsingReduce_thenMultipleMapsMergedIntoOneMap() {

        Map<String, Integer> expectedMap = new HashMap<String, Integer>() {{
            put("Saajan", 35);
            put("Liam", 100);
            put("Kai", 92); // max of 92, 76, and null
            put("Eric", 42);
            put("Kevin", 77);
            put("Jerry", null);
        }};
        Stream<Map<String, Integer>> mapStream = getMapStream(playerMap1, playerMap2, playerMap3, playerMap4, playerMap5);
        Map<String, Integer> mergedMap = mapStream.reduce(new HashMap<>(), (resultMap, theMap) -> {
            theMap.forEach((k, v) -> {
                if (resultMap.containsKey(k)) {
                    resultMap.put(k, maxInteger(resultMap.get(k), v));
                } else {
                    resultMap.put(k, v);
                }
            });
            return resultMap;
        });

        assertEquals(expectedMap, mergedMap);

    }
}