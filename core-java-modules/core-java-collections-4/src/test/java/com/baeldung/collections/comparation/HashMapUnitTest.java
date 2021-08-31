package com.baeldung.collections.comparation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HashMapUnitTest {

    @Test
    void givenHashMap_whenItemAddedByKey_thenItCanBeRetrieved() {
        Map<String, String> map = new HashMap<>();
        map.put("123456", "Daniel");
        map.put("654321", "Marko");
        assertThat(map.get("654321")).isEqualTo("Marko");
    }

    @Test
    void givenHashMap_whenItemRemovedByKey_thenMapSizeIsReduced() {
        Map<String, String> map = new HashMap<>();
        map.put("123456", "Daniel");
        map.put("654321", "Marko");
        map.remove("654321");
        assertThat(map).hasSize(1);
    }

}
