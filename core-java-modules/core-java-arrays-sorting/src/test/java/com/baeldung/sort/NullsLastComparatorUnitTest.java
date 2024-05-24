package com.baeldung.sort;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NullsLastComparatorUnitTest {

    @Test
    public void givenStringArray_whenSortingWithNull_thenCorrectlySorted() {
        List<String> strings = new ArrayList<>();
        strings.add("DD");
        strings.add("BB");
        strings.add("AA");
        strings.add("EE");
        strings.add(null);

        Comparator<String> nullsLastComparator = Comparator.nullsLast(Comparator.naturalOrder());
        Collections.sort(strings, nullsLastComparator);

        assertEquals("AA", strings.get(0));
        assertEquals("BB", strings.get(1));
        assertEquals("DD", strings.get(2));
        assertEquals("EE", strings.get(3));
        assertNull(strings.get(4));
    }
}
