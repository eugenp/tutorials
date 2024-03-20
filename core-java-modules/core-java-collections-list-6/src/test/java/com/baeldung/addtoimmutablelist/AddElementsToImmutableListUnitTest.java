package com.baeldung.addtoimmutablelist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AddElementsToImmutableListUnitTest {

    public static <T> List<T> appendAnElement(List<T> immutableList, T element) {
        List<T> tmpList = new ArrayList<>(immutableList);
        tmpList.add(element);
        return Collections.unmodifiableList(tmpList);
    }

    @SafeVarargs
    public static <T> List<T> appendElements(List<T> immutableList, T... elements) {
        List<T> tmpList = new ArrayList<>(immutableList);
        tmpList.addAll(Arrays.asList(elements));
        return Collections.unmodifiableList(tmpList);
    }

    @Test
    void whenCallingAppendAnElement_thenGetExpectedResult() {
        List<String> myList = List.of("A", "B", "C", "D", "E");
        List<String> expected = List.of("A", "B", "C", "D", "E", "F");
        List<String> result = appendAnElement(myList, "F");
        assertThat(result).isEqualTo(expected)
            .isUnmodifiable();
    }

    @Test
    void whenCallingAppendElements_thenGetExpectedResult() {
        List<String> myList = List.of("A", "B", "C", "D", "E");
        List<String> expected1 = List.of("A", "B", "C", "D", "E", "F");
        List<String> result1 = appendElements(myList, "F");
        assertThat(result1).isEqualTo(expected1)
            .isUnmodifiable();

        List<String> expected2 = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I");
        List<String> result2 = appendElements(myList, "F", "G", "H", "I");
        assertThat(result2).isEqualTo(expected2)
            .isUnmodifiable();
    }
}