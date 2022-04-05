package com.baeldung.collections.hashset;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class HashSetUnitTest {

    @Test
    void whenRemoveAllFromHashset_thenRemovesAllElementsFromHashsetThatArePresentInCollection() {
        Set<Integer> set = new HashSet<>();
        Collection<Integer> collection = new ArrayList<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        collection.add(1);
        collection.add(3);

        set.removeAll(collection);

        assertEquals(2, set.size());
        Integer[] actualElements = new Integer[set.size()];
        Integer[] expectedElements = new Integer[] { 2, 4 };
        assertArrayEquals(expectedElements, set.toArray(actualElements));
    }

}
