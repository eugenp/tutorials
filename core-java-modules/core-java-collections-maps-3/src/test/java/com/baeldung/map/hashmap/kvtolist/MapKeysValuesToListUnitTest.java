package com.baeldung.map.hashmap.kvtolist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapKeysValuesToListUnitTest {
    private static final HashMap<String, String> DEV_MAP;

    static {
        DEV_MAP = new HashMap<>();
        DEV_MAP.put("Kent", "Linux");
        DEV_MAP.put("Eric", "MacOS");
        DEV_MAP.put("Kevin", "Windows");
        DEV_MAP.put("Michal", "MacOS");
        DEV_MAP.put("Saajan", "Linux");
    }

    @Test
    void whenUsingKeySet_thenGetExpectedResult() {
        List<String> keyList = new ArrayList<>(DEV_MAP.keySet());
        // this assertion may fail, since hashMap doesn't preserve the insertion order
        // assertEquals(Lists.newArrayList("Kent", "Eric", "Kevin", "Michal", "Saajan"), keyList);

        assertThat(keyList).containsExactlyInAnyOrder("Kent", "Eric", "Kevin", "Michal", "Saajan");

    }

    @Test
    void whenUsingValues_thenGetExpectedResult() {
        List<String> valueList = new ArrayList<>(DEV_MAP.values());
        assertThat(valueList).containsExactlyInAnyOrder("Linux", "MacOS", "Windows", "MacOS", "Linux");
    }

    @Test
    void whenLoopingEntries_thenGetExpectedResult() {
        List<String> keyList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        for (Map.Entry<String, String> entry : DEV_MAP.entrySet()) {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }

        assertKeyAndValueList(keyList, valueList);

    }

    @Test
    void whenUsingForEach_thenGetExpectedResult() {
        List<String> keyList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        DEV_MAP.forEach((k, v) -> {
            keyList.add(k);
            valueList.add(v);
        });

        assertKeyAndValueList(keyList, valueList);
    }

    private void assertKeyAndValueList(List<String> keyList, List<String> valueList) {
        assertThat(keyList).containsExactlyInAnyOrder("Kent", "Eric", "Kevin", "Michal", "Saajan");
        assertThat(valueList).containsExactlyInAnyOrder("Linux", "MacOS", "Windows", "MacOS", "Linux");
        for (int i = 0; i < keyList.size(); i++) {
            assertThat(DEV_MAP).containsEntry(keyList.get(i), valueList.get(i));
        }
    }
}