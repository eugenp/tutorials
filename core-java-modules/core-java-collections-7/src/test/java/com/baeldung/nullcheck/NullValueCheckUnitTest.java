package com.baeldung.nullcheck;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class NullValueCheckUnitTest {

    Function<Collection<?>, Long> countNulls = collection -> collection.stream()
        .filter(Objects::isNull)
        .count();

    @Test
    void givenList_whenNullValueAdded_doesNotFail() {
        // adding nulls to a list
        Integer[] numberArray = { null, 0, 1, null, 2, 3, null };
        List<Integer> numbers = Arrays.asList(numberArray);
        assertEquals(3, countNulls.apply(numbers));

        // accessing nulls from a list
        final Integer number = numbers.get(0);
        assertNull(number);

        // dereferencing nulls from a list
        assertThrows(NullPointerException.class, () -> number.toString());
    }

    @Test
    void givenHashSet_whenNullValueAdded_doesNotFail() {
        // adding nulls to a HashSet
        Integer[] numberArray = { null, 0, 1, null, 2, 3, null };
        Set<Integer> numbers = new HashSet<>(Arrays.asList(numberArray));
        assertEquals(1, countNulls.apply(numbers));

        // accessing null from a set
        assertTrue(numbers.contains(null));

        // dereferencing nulls from a set
        assertThrows(NullPointerException.class, () -> numbers.forEach(Object::toString));
    }

    @Test
    void givenTreeSet_whenNullValueAdded_mightFail() {
        // adding nulls to a TreeSet
        Integer[] numberArray = { null, 0, 1, null, 2, 3, null };
        assertThrows(NullPointerException.class, () -> new TreeSet<>(Arrays.asList(numberArray)));
    }

    @Test
    void givenHashMap_whenNullKeyValueAdded_doesNotFail() {
        // adding nulls to key value pairs
        Integer[] numberArray = { null, 0, 1, null, 2, 3, null };
        Map<Integer, Integer> numbers = new HashMap<>();
        Arrays.stream(numberArray)
            .forEach(integer -> numbers.put(integer, integer));
        assertEquals(1, countNulls.apply(numbers.keySet()));
        assertEquals(1, countNulls.apply(numbers.values()));

        // accessing nulls from a map
        assertTrue(numbers.containsKey(null));
        assertTrue(numbers.containsValue(null));
        assertNull(numbers.get(null));

        // dereferencing nulls from a map
        assertThrows(NullPointerException.class, () -> numbers.get(null)
            .toString());
    }

    @Test
    void givenTreeMap_whenNullKeyAdded_fails() {
        // adding nulls to key value pairs
        Map<Integer, Integer> numbers = new TreeMap<>();
        // adding null key and null value
        assertThrows(NullPointerException.class, () -> numbers.put(null, null));

        // adding null key and non-null value
        assertThrows(NullPointerException.class, () -> numbers.put(null, 1));

        // adding non-null key and null value
        assertDoesNotThrow(() -> numbers.put(1, null));

        // adding non-null key and non-null value
        assertDoesNotThrow(() -> numbers.put(1, 1));
    }
}
