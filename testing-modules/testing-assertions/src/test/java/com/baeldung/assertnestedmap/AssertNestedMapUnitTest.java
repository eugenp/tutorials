package com.baeldung.assertnestedmap;


import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.baeldung.assertnestedmap.matchers.NestedMapMatcher.hasNestedMapEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssertNestedMapUnitTest {
    @Test
    void givenNestedMap_whenUseJupiterAssertTrueWithoutCasting_thenTest() {
        Map<String, Object> innerMap = Map.of("city", "Chicago");
        Map<String, Map<String, Object>> outerMap = Map.of("address", innerMap);

        assertTrue(outerMap.containsKey("address") && outerMap.get("address").get("city").equals("Chicago"));
    }

    @Test
    void givenNestedMap_whenUseJupiterAssertAllAndAssertTrue_thenTest() {
        Map<String, Object> innerMap = Map.of("city", "Chicago");
        Map<String, Map<String, Object>> outerMap = Map.of("address", innerMap);

        assertAll(
          () -> assertTrue(outerMap.containsKey("address")),
          () -> assertEquals(outerMap.get("address").get("city"), "Chicago")
        );
    }

    @Test
    void givenNestedMap_whenUseJupiterAssertTrueWithCasting_thenTest() {
        Map<String, Object> innerMap = Map.of("city", "Chicago");
        Map<String, Object> outerMap = Map.of("address", innerMap);

        assertTrue(outerMap.containsKey("address")
                && ((Map<String, Object>)outerMap.get("address")).get("city").equals("Chicago"));
    }

    @Test
    void givenNestedMap_whenUseHamcrestAssertThat_thenTest() {
        Map<String, Object> innerMap = Map.of("city", "Chicago");
        Map<String, Map<String, Object>> outerMap = Map.of("address", innerMap);
        assertAll(
          () -> assertThat(outerMap, hasKey("address")),
          () -> assertThat(outerMap.get("address"), hasEntry("city", "Chicago"))
        );
    }

    @Test
    void givenNestedMapOfStringAndObject_whenUseHamcrestAssertThat_thenTest() {
        Map<String, Object> innerMap = Map.of("city", "Chicago");
        Map<String, Map<String, Object>> outerMap = Map.of("address", innerMap);

        assertThat(outerMap, hasEntry(equalTo("address"), hasEntry("city", "Chicago")));
    }

    @Test
    void givenNestedMapOfStringAndObject_whenUseHamcrestAssertThatAndCustomMatcher_thenTest() {
        Map<String, Object> innerMap = Map.of
          (
            "city", "Chicago",
            "zip", "10005"
          );
        Map<String, Map<String, Object>> outerMap = Map.of("address", innerMap);

        assertThat(outerMap, hasNestedMapEntry("address", innerMap));
    }

    @Test
    void givenOuterMapOfStringAndObjectAndInnerMap_whenUseHamcrestAssertThatAndCustomMatcher_thenTest() {
        Map<String, Object> innerMap = Map.of
          (
            "city", "Chicago",
            "zip", "10005"
          );
        Map<String, Object> outerMap = Map.of("address", innerMap);

        assertThat(outerMap, hasNestedMapEntry("address", innerMap));
    }

    @Test
    void givenNestedMap_whenUseHamcrestAssertThatWithCasting_thenTest() {
        Map<String, Object> innerMap = Map.of("city", "Chicago");
        Map<String, Object> outerMap = Map.of("address", innerMap);

        assertThat((Map<String, Object>)outerMap.get("address"), hasEntry("city", "Chicago"));
    }

}
