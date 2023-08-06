package com.baeldung.map.removeuplicate;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

public class RemoveDuplicateValuesUnitTest {
    private Map<String, String> initDevMap() {
        Map<String, String> devMap = new HashMap<>();
        devMap.put("Tom", "Linux");
        devMap.put("Kent", "Linux");

        devMap.put("Bob", "MacOS");
        devMap.put("Eric", "MacOS");

        devMap.put("Peter", "Windows");
        devMap.put("Saajan", "Windows");
        devMap.put("Jan", "Windows");

        devMap.put("Kevin", "FreeBSD");
        return devMap;
    }

    @Test
    void whenUsingReverseMap_thenDuplicateValuesAreRemoved() {
        Map<String, String> devMap = initDevMap();
        Map<String, String> tmpReverseMap = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        for (String name : devMap.keySet()) {
            tmpReverseMap.put(devMap.get(name), name);
        }

        for (String os : tmpReverseMap.keySet()) {
            result.put(tmpReverseMap.get(os), os);
        }

        assertThat(result.values()).hasSize(4)
          .containsExactlyInAnyOrder("Windows", "MacOS", "Linux", "FreeBSD");
    }

    @Test
    void whenUsingStream_thenDuplicateValuesAreRemoved() {
        Map<String, String> devMap = initDevMap();
        Map<String, String> result = devMap.entrySet()
          .stream()
          .collect(toMap(Map.Entry::getValue, Map.Entry::getKey, (keyInMap, keyNew) -> keyInMap))
          .entrySet()
          .stream()
          .collect(toMap(Map.Entry::getValue, Map.Entry::getKey));

        assertThat(result.values()).hasSize(4)
          .containsExactlyInAnyOrder("Windows", "MacOS", "Linux", "FreeBSD");
    }

    @Test
    void whenUsingStream_thenDuplicateValuesAreRemovedAndOnlyLongestNamesExist() {
        Map<String, String> devMap = initDevMap();

        //@formatter:off
        Map<String, String> expected = ImmutableMap.of(
          "Eric", "MacOS",
          "Kent", "Linux",
          "Saajan", "Windows",
          "Kevin", "FreeBSD");
        //@formatter:on

        Map<String, String> result = devMap.entrySet()
          .stream()
          .collect(toMap(Map.Entry::getValue, Map.Entry::getKey, (k1, k2) -> k1.length() > k2.length() ? k1 : k2))
          .entrySet()
          .stream()
          .collect(toMap(Map.Entry::getValue, Map.Entry::getKey));

        assertThat(result).hasSize(4)
          .isEqualTo(expected);
    }
}