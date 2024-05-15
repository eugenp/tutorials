package com.baeldung.map.generictypeconversion;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

public class ConvertMapWithTypeParamUnitTest {
    private static final Map<String, Object> MAP1 = Maps.newHashMap();

    static {
        MAP1.put("K01", "GNU Linux");
        MAP1.put("K02", "Mac OS");
        MAP1.put("K03", "MS Windows");
    }

    private static final Map<String, String> EXPECTED_MAP1 = Maps.newHashMap();

    static {
        EXPECTED_MAP1.put("K01", "GNU Linux");
        EXPECTED_MAP1.put("K02", "Mac OS");
        EXPECTED_MAP1.put("K03", "MS Windows");
    }

    private static final Map<String, Object> MAP2 = Maps.newHashMap();

    static {
        MAP2.put("K01", "GNU Linux");
        MAP2.put("K02", "Mac OS");
        MAP2.put("K03", BigDecimal.ONE);
    }

    private static final Map<String, String> EXPECTED_MAP2_STRING_VALUES = Maps.newHashMap();

    static {
        EXPECTED_MAP2_STRING_VALUES.put("K01", "GNU Linux");
        EXPECTED_MAP2_STRING_VALUES.put("K02", "Mac OS");
        EXPECTED_MAP2_STRING_VALUES.put("K03", "1");
    }

    @Test
    void whenCastingToMap_shouldGetExpectedResult() {
        Map<String, String> result = (Map) MAP1;
        assertEquals(EXPECTED_MAP1, result);

        Map<String, String> result2 = (Map) MAP2;
        assertFalse(result2.get("K03") instanceof String);
    }

    Map<String, String> checkAndTransform(Map<String, Object> inputMap) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            try {
                result.put(entry.getKey(), (String) entry.getValue());
            } catch (ClassCastException e) {
                throw e;
            }
        }
        return result;
    }

    @Test
    void whenCheckAndTransform_shouldGetExpectedResult() {
        Map<String, String> result = checkAndTransform(MAP1);
        assertEquals(EXPECTED_MAP1, result);

        assertThrows(ClassCastException.class, () -> checkAndTransform(MAP2));
    }

    @Test
    void whenUsingStringValueOf_shouldGetExpectedResult() {
        Map<String, String> result = MAP1.entrySet()
          .stream()
          .collect(toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));

        assertEquals(EXPECTED_MAP1, result);

        Map<String, String> result2 = MAP2.entrySet()
          .stream()
          .collect(toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));

        assertEquals(EXPECTED_MAP2_STRING_VALUES, result2);
    }
}