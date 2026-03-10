package com.baeldung.map.arraylistinhashmap;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;

import org.junit.Test;
import java.util.Collection;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class ArrayListInHashMapUnitTest {

    private static final String K1 = "key1";
    private static final String K2 = "key2";
    private static final String K1_V1 = "key1_value1";
    private static final String K1_V2 = "key1_value2";
    private static final String K2_V1 = "key2_value1";

    private static void verifyMap(Map<String, ? extends Collection<String>> testMap) {
        assertEquals(Map.of(K1, List.of(K1_V1, K1_V2), K2, List.of(K2_V1)), testMap);
    }

    @Test
    void whenUsingAddKeyManually_thenMapMatches() {
        Map<String, List<String>> map = new HashMap<>();
        ArrayListInHashMap.addKeyManually(map, K1, K1_V1);
        ArrayListInHashMap.addKeyManually(map, K1, K1_V2);
        ArrayListInHashMap.addKeyManually(map, K2, K2_V1);
        verifyMap(map);
    }

    @Test
    void whenUsingComputeIfAbsent_thenMapMatches() {
        Map<String, List<String>> map = new HashMap<>();
        ArrayListInHashMap.addKeyWithComputeIfAbsent(map, K1, K1_V1);
        ArrayListInHashMap.addKeyWithComputeIfAbsent(map, K1, K1_V2);
        ArrayListInHashMap.addKeyWithComputeIfAbsent(map, K2, K2_V1);
        verifyMap(map);
    }

    @Test
    void whenUsingApacheMultiValuedMap_thenMapMatches() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        ArrayListInHashMap.addKeyToApacheMultiValuedMap(map, K1, K1_V1);
        ArrayListInHashMap.addKeyToApacheMultiValuedMap(map, K1, K1_V2);
        ArrayListInHashMap.addKeyToApacheMultiValuedMap(map, K2, K2_V1);
        verifyMap(map.asMap());
    }

    @Test
    void whenUsingSpringLinkedMultiValueMap_thenMapMatches() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        ArrayListInHashMap.addKeyToSpringLinkedMultiValueMap(map, K1, K1_V1);
        ArrayListInHashMap.addKeyToSpringLinkedMultiValueMap(map, K1, K1_V2);
        ArrayListInHashMap.addKeyToSpringLinkedMultiValueMap(map, K2, K2_V1);
        verifyMap(map);
    }

    @Test
    void whenUsingGuavaMultimap_thenMapMatches() {
        Multimap<String, String> map = ArrayListMultimap.create();
        ArrayListInHashMap.addKeyToGuavaMultimap(map, K1, K1_V1);
        ArrayListInHashMap.addKeyToGuavaMultimap(map, K1, K1_V2);
        ArrayListInHashMap.addKeyToGuavaMultimap(map, K2, K2_V1);
        verifyMap(map.asMap());
    }

}