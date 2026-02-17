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

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final List<String> VALUE_1 = List.of("key1_value1", "key1_value2");
    private static final List<String> VALUE_2 = List.of("key2_value1");

    private void verifyMap(Map<String, ? extends Collection<String>> actualMap) {
        assertNotNull(actualMap);
        assertEquals(2, actualMap.size());
        assertTrue(actualMap.containsKey(KEY_1));
        assertEquals(VALUE_1, new ArrayList<>(actualMap.get(KEY_1)));
        assertTrue(actualMap.containsKey(KEY_2));
        assertEquals(VALUE_2, new ArrayList<>(actualMap.get(KEY_2)));
    }

    @Test
    void whenUsingWithoutExternalLibraries_thenMapMatches() {
        Map<String, ArrayList<String>> map = new ArrayListInHashMap().withoutExternalLibraries();
        verifyMap(map);
    }

    @Test
    void whenWithoutExternalLibrariesForLoop_thenMapMatches() {
        Map<String, ArrayList<String>> map = new ArrayListInHashMap().withoutExternalLibrariesForLoop();
        verifyMap(map);
    }

    @Test
    void whenWithComputeIfAbsent_thenMapMatches() {
        Map<String, ArrayList<String>> map = new ArrayListInHashMap().withComputeIfAbsent();
        verifyMap(map);
    }

    @Test
    void whenWithComputeIfAbsentIterables_thenMapMatches() {
        Map<String, ArrayList<String>> map = new ArrayListInHashMap().withComputeIfAbsentIterables();
        verifyMap(map);
    }

    @Test
    void whenWithApacheMultiValuedMap_thenMapMatches() {
        MultiValuedMap<String, String> map = new ArrayListInHashMap().withApacheMultiValuedMap();
        verifyMap(map.asMap());
    }

    @Test
    void whenWithSpringLinkedMultiValueMap_thenMapMatches() {
        MultiValueMap<String, String> map = new ArrayListInHashMap().withSpringLinkedMultiValueMap();
        verifyMap(map);
    }

    @Test
    void whenWithGuavaMultimap_thenMapMatches() {
        Multimap<String, String> map = new ArrayListInHashMap().withGuavaMultimap();
        verifyMap(map.asMap());
    }
}