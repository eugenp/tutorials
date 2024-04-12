package com.baeldung.collect.nullable;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;

import org.junit.jupiter.api.Test;

public class CanCollectReturnNullUnitTest {
    private final List<String> LANGUAGES = Arrays.asList("Kotlin", null, null, "Java", "Python", "Rust");

    @Test
    void givenAStreamWithNullElements_whenCollect_shouldReturnNotNull() {
        List<String> result = LANGUAGES.stream()
          .filter(Objects::isNull)
          .collect(toList());
        assertNotNull(result);
        assertEquals(Arrays.asList(null, null), result);
    }

    @Test
    void givenAStreamWithNullElements_whenCollectEmptyStream_shouldReturnNotNull() {
        List<String> result = LANGUAGES.stream()
          .filter(s -> s != null && s.length() == 1)
          .collect(toList());
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Map<Character, String> result2 = LANGUAGES.stream()
          .filter(s -> s != null && s.length() == 1)
          .collect(toMap(s -> s.charAt(0), Function.identity()));
        assertNotNull(result2);
        assertTrue(result2.isEmpty());

        Map<Character, List<String>> result3 = LANGUAGES.stream()
          .filter(s -> s != null && s.length() == 1)
          .collect(groupingBy(s -> s.charAt(0)));
        assertNotNull(result3);
        assertTrue(result3.isEmpty());
    }

    @Test
    void givenAStream_whenCollectByEmptyToNullCollector_shouldReturnExpectedResults() {
        Collector<String, ArrayList<String>, ArrayList<String>> emptyListToNullCollector = Collector.of(ArrayList::new, ArrayList::add, (a, b) -> {
            a.addAll(b);
            return a;
        }, a -> a.isEmpty() ? null : a);

        List<String> notNullResult = LANGUAGES.stream()
          .filter(Objects::isNull)
          .collect(emptyListToNullCollector);
        assertNotNull(notNullResult);
        assertEquals(Arrays.asList(null, null), notNullResult);

        List<String> nullResult = LANGUAGES.stream()
          .filter(s -> s != null && s.length() == 1)
          .collect(emptyListToNullCollector);
        assertNull(nullResult);
    }

    @Test
    void givenAStream_whenCollectByCollectingAndThen_shouldReturnExpectedResults() {
        List<String> notNullResult = LANGUAGES.stream()
          .filter(Objects::nonNull)
          .collect(collectingAndThen(toList(), Collections::unmodifiableList));
        assertNotNull(notNullResult);
        assertEquals(Arrays.asList("Kotlin", "Java", "Python", "Rust"), notNullResult);

        //the result list becomes immutable
        assertThrows(UnsupportedOperationException.class, () -> notNullResult.add("Oops"));

        List<String> nullResult = LANGUAGES.stream()
          .filter(s -> s != null && s.length() == 1)
          .collect(collectingAndThen(toList(), strings -> strings.isEmpty() ? null : strings));
        assertNull(nullResult);
    }
}