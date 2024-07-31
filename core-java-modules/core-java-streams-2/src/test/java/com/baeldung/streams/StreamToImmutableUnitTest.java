package com.baeldung.streams;

import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StreamToImmutableUnitTest {

    @Test
    void whenUsingCollectingToImmutableSet_thenSuccess() {
        List<String> givenList = Arrays.asList("a", "b", "c");
        List<String> result = givenList.stream()
            .collect(collectingAndThen(toSet(), ImmutableList::copyOf));

        assertEquals("com.google.common.collect.RegularImmutableList", result.getClass().getName());
    }

    @Test
    void whenUsingCollectingToUnmodifiableList_thenSuccess() {
        List<String> givenList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> result = givenList.stream()
            .collect(collectingAndThen(toList(), Collections::unmodifiableList));

        assertEquals("java.util.Collections$UnmodifiableRandomAccessList", result.getClass().getName());
    }

    @Test
    void whenCollectToImmutableList_thenSuccess() {
        List<Integer> list = IntStream.range(0, 9)
            .boxed()
            .collect(ImmutableList.toImmutableList());

        assertEquals("com.google.common.collect.RegularImmutableList", list.getClass().getName());
    }

    @Test
    void whenCollectToMyImmutableListCollector_thenSuccess() {
        List<String> givenList = Arrays.asList("a", "b", "c", "d");
        List<String> result = givenList.stream()
            .collect(MyImmutableListCollector.toImmutableList());

        assertEquals("java.util.Collections$UnmodifiableRandomAccessList", result.getClass().getName());
    }

    @Test
    void whenPassingSupplier_thenSuccess() {
        List<String> givenList = Arrays.asList("a", "b", "c", "d");
        List<String> result = givenList.stream()
            .collect(MyImmutableListCollector.toImmutableList(LinkedList::new));

        assertEquals("java.util.Collections$UnmodifiableList", result.getClass().getName());
    }
}
