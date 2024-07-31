package com.baeldung.map.valuetokeyset;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

public class ConvertMapKeyValueToMapValueKeySetUnitTest {

    private static final Map<String, String> INPUT_MAP = Map.of(
        // @formatter:off
        "Kai", "Linux",
        "Eric", "MacOS",
        "Kevin", "Windows",
        "Liam", "MacOS",
        "David", "Linux",
        "Saajan", "Windows",
        "Loredana", "MacOS"
        // @formatter:on
    );

    private static final Map<String, Set<String>> EXPECTED = Map.of(
        // @formatter:off
        "Linux", Set.of("Kai", "David"),
        "Windows", Set.of("Saajan", "Kevin"),
        "MacOS", Set.of("Eric", "Liam", "Loredana")
        // @formatter:on
    );

    private static final Map<String, String> INPUT_MAP_WITH_NULLS = new HashMap<String, String>(INPUT_MAP) {{
        put("Tom", null);
        put("Jerry", null);
        put(null, null);
    }};

    private static final Map<String, Set<String>> EXPECTED_WITH_NULLS = new HashMap<String, Set<String>>(EXPECTED) {{
        put(null, new HashSet<String>() {{
            add("Tom");
            add("Jerry");
            add(null);
        }});
    }};

    public static <K, V> Map<V, Set<K>> transformMap(Map<K, V> input) {
        Map<V, Set<K>> resultMap = new HashMap<>();
        for (Map.Entry<K, V> entry : input.entrySet()) {
            if (!resultMap.containsKey(entry.getValue())) {
                resultMap.put(entry.getValue(), new HashSet<>());
            }
            resultMap.get(entry.getValue())
                .add(entry.getKey());
        }
        return resultMap;
    }

    @Test
    void whenUsingClassicLoopBasedSolution_thenGetExpectedResult() {
        Map<String, Set<String>> result = transformMap(INPUT_MAP);
        assertEquals(EXPECTED, result);

        Map<String, Set<String>> result2 = transformMap(INPUT_MAP_WITH_NULLS);
        assertEquals(EXPECTED_WITH_NULLS, result2);
    }

    @Test
    void whenUsingJava8StreamGroupingBy_thenGetExpectedResult() {
        Map<String, Set<String>> result = INPUT_MAP.entrySet()
            .stream()
            .collect(groupingBy(Map.Entry::getValue, mapping(Map.Entry::getKey, toSet())));
        assertEquals(EXPECTED, result);

        assertThrows(NullPointerException.class, () -> INPUT_MAP_WITH_NULLS.entrySet()
            .stream()
            .collect(groupingBy(Map.Entry::getValue, mapping(Map.Entry::getKey, toSet()))));
    }

    @Test
    void whenUsingJava8ForEach_thenGetExpectedResult() {
        Map<String, Set<String>> result = new HashMap<>();
        INPUT_MAP.forEach((key, value) -> result.computeIfAbsent(value, k -> new HashSet<>())
            .add(key));
        assertEquals(EXPECTED, result);

        Map<String, Set<String>> result2 = new HashMap<>();
        INPUT_MAP_WITH_NULLS.forEach((key, value) -> result2.computeIfAbsent(value, k -> new HashSet<>())
            .add(key));
        assertEquals(EXPECTED_WITH_NULLS, result2);
    }

    @Test
    void whenUsingGuavaMultiMapCollector_thenGetExpectedResult() {
        Map<String, Set<String>> result = INPUT_MAP.entrySet()
            .stream()
            .collect(collectingAndThen(Multimaps.toMultimap(Map.Entry::getValue, Map.Entry::getKey, HashMultimap::create), Multimaps::asMap));
        assertEquals(EXPECTED, result);

        Map<String, Set<String>> result2 = INPUT_MAP_WITH_NULLS.entrySet()
            .stream()
            .collect(collectingAndThen(Multimaps.toMultimap(Map.Entry::getValue, Map.Entry::getKey, HashMultimap::create), Multimaps::asMap));
        assertEquals(EXPECTED_WITH_NULLS, result2);
    }

    @Test
    void whenUsingGuavaInvertFromAndForMap_thenGetExpectedResult() {
        SetMultimap<String, String> multiMap = Multimaps.invertFrom(Multimaps.forMap(INPUT_MAP), HashMultimap.create());
        Map<String, Set<String>> result = Multimaps.asMap(multiMap);
        assertEquals(EXPECTED, result);

        SetMultimap<String, String> multiMapWithNulls = Multimaps.invertFrom(Multimaps.forMap(INPUT_MAP_WITH_NULLS), HashMultimap.create());
        Map<String, Set<String>> result2 = Multimaps.asMap(multiMapWithNulls);
        assertEquals(EXPECTED_WITH_NULLS, result2);
    }

}