package com.baeldung.iteratorvsforeach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IteratorVsForeachUnitTest {

    private static Stream<Arguments> listProvider() {
        return Stream.of(Arguments.of(List.of("String1", "String2", "unwanted"), List.of("String1", "String2")));
    }

    @Test
    public void givenEmptyCollection_whenUsingForEach_thenNoElementsAreIterated() {
        List<String> names = Collections.emptyList();
        StringBuilder stringBuilder = new StringBuilder();
        names.forEach(stringBuilder::append);
        assertEquals("", stringBuilder.toString());
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    public void givenCollectionWithElements_whenRemovingElementDuringForEachIteration_thenElementIsRemoved(List<String> input, List<String> expected) {
        List<String> mutableList = new ArrayList<>(input);
        // Separate collection for items to be removed
        List<String> toRemove = new ArrayList<>();

        // Using forEach to identify items to remove
        input.forEach(item -> {
            if (item.equals("unwanted")) {
                toRemove.add(item);
            }
        });

        // Removing the identified items from the original list
        mutableList.removeAll(toRemove);
        assertIterableEquals(expected, mutableList);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    public void givenCollectionWithElements_whenRemovingElementDuringIteratorIteration_thenElementIsRemoved(List<String> input, List<String> expected) {
        List<String> mutableList = new ArrayList<>(input);
        Iterator<String> it = mutableList.iterator();
        while (it.hasNext()) {
            String item = it.next();
            if (item.equals("unwanted")) {
                it.remove(); // Safely remove item
            }
        }
        assertIterableEquals(expected, mutableList);
    }

}
