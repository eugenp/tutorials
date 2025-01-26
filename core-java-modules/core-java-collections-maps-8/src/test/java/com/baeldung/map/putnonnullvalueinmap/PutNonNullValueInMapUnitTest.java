package com.baeldung.map.putnonnullvalueinmap;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PutNonNullValueInMapUnitTest {

    private Map<Integer, String> map;
    private String nullValue;

    @Test
    void givenMapWithNullValue_whenUsingStreamFilter_thenObtainMapWithNonNullValues() {
        final Map<Integer, String> nullValueMap = new HashMap<>();
        nullValueMap.put(1, nullValue);

        final Map<Integer, String> mapWithoutNullValues = nullValueMap.entrySet()
            .stream()
            .filter(entry -> Objects.nonNull(entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        assertThat(mapWithoutNullValues).isEmpty();
    }

    @Test
    void givenNullValue_whenUsingIfStatementWithPutMethod_thenObtainMapWithNonNullValues() {
        if (nullValue != null) {
            map.put(1, nullValue);
        }
        assertThat(map).isEmpty();
    }

    @Test
    void givenNullValue_whenUsingOptionalIfPresentWithPutMethod_thenObtainMapWithNonNullValues() {
        Optional.ofNullable(nullValue)
            .ifPresent(value -> map.put(1, value));
        assertThat(map).isEmpty();
    }

    @BeforeEach
    void init() {
        nullValue = null;
        map = new HashMap<>();
    }
}