package com.baeldung.arrayconversion;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class ListToArrayFirstNElementsUnitTest {
    private static final List<String> INPUT_LIST = Lists.newArrayList("one", "two", "three", "four", "five", "six", "seven");
    private static final int n = 5;

    @Test
    void whenUsingForLoop_thenGetExpectedArray() {
        String[] result = new String[n];
        for (int i = 0; i < n; i++) {
            result[i] = INPUT_LIST.get(i);
        }
        assertArrayEquals(new String[] { "one", "two", "three", "four", "five" }, result);

        String[] result2 = new String[n];
        Iterator<String> iterator = INPUT_LIST.iterator();
        for (int i = 0; i < n && iterator.hasNext(); i++) {
            result2[i] = iterator.next();
        }
        assertArrayEquals(new String[] { "one", "two", "three", "four", "five" }, result2);
    }

    @Test
    void whenUsingSubList_thenGetExpectedArray() {
        String[] result = new String[n];
        INPUT_LIST.subList(0, n)
          .toArray(result);
        assertArrayEquals(new String[] { "one", "two", "three", "four", "five" }, result);

        String[] result2 = INPUT_LIST.subList(0, n)
          .toArray(new String[0]);
        assertArrayEquals(new String[] { "one", "two", "three", "four", "five" }, result2);

        // available only for java 11+
        String[] result3 = INPUT_LIST.subList(0, n)
          .toArray(String[]::new);
        assertArrayEquals(new String[] { "one", "two", "three", "four", "five" }, result3);
    }

    @Test
    void whenUsingStream_thenGetExpectedArray() {
        String[] result = INPUT_LIST.stream()
          .limit(n)
          .toArray(String[]::new);
        assertArrayEquals(new String[] { "one", "two", "three", "four", "five" }, result);
    }
}