package com.baeldung.linkedhashsetindexof;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class LinkedHashSetIndexLookupUnitTest {

    @Test
    public void givenLinkedHashSet_whenGetIndex_thenReturnsCorrectIndex() {
        LinkedHashSet<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        int index = LinkedHashSetIndexLookup.getIndex(names, "John");
        assertEquals(1, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexOfFirstElement_thenReturnsZero() {
        LinkedHashSet<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        int index = LinkedHashSetIndexLookup.getIndex(names, "Mike");
        assertEquals(0, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexOfLastElement_thenReturnsLastIndex() {
        LinkedHashSet<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        int index = LinkedHashSetIndexLookup.getIndex(names, "Karen");
        assertEquals(2, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexOfNonExistentElement_thenReturnsNegativeOne() {
        LinkedHashSet<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        int index = LinkedHashSetIndexLookup.getIndex(names, "Alice");
        assertEquals(-1, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexUsingIterator_thenReturnsCorrectIndex() {
        LinkedHashSet<Integer> numbers = new LinkedHashSet<>();
        numbers.add(100);
        numbers.add(20);
        numbers.add(300);

        int index = LinkedHashSetIndexLookup.getIndexUsingIterator(numbers, 20);
        assertEquals(1, index);
    }

    @Test
    public void givenEmptyLinkedHashSet_whenGetIndex_thenReturnsNegativeOne() {
        LinkedHashSet<String> emptySet = new LinkedHashSet<>();
        int index = LinkedHashSetIndexLookup.getIndex(emptySet, "Any");
        assertEquals(-1, index);
    }
}


