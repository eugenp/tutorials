package com.baeldung.map.computeifabsent;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ComputeIfAbsentUnitTest {
        @Test public void whenKeyIsPresent_thenFetchTheValue() {
                Map<String, Integer> stringLength = new HashMap<>();
                stringLength.put("John", 4);
                assertTrue(stringLength.computeIfAbsent("John", s -> s.length()) == 4);
        }

        @Test public void whenKeyIsNotPresent_thenComputeTheValueUsingMappingFunctionAndStore() {
                Map<String, Integer> stringLength = new HashMap<>();
                assertTrue(stringLength.computeIfAbsent("John", s -> s.length()) == 4);
                assertTrue(stringLength.get("John") == 4);
        }
}
