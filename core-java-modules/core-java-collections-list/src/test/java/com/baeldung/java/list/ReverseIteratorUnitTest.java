package com.baeldung.java.list;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class ReverseIteratorUnitTest {

    private final ReverseIterator reverseIterator = new ReverseIterator();

    private List<String> list;

    private final String originalString = "ABCDE";

    @BeforeEach
    void setUp() {

        list = Lists.newArrayList("A", "B", "C", "D", "E");
    }

    @Test
    void whenIteratingUsingForLoop_thenCorrect() {

        String reverseString = "";
        for (int i = list.size(); i-- > 0; ) {
            reverseString += list.get(i);
        }
        assertEquals(reverseString, StringUtils.reverse(originalString));
    }

    @Test
    void whenIteratingUsingListIterator_thenCorrect() {

        String reverseString = "";
        final ListIterator listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            reverseString += listIterator.previous();
        }
        assertEquals(reverseString, StringUtils.reverse(originalString));
    }

    @Test
    void whenIteratingUsingCollections_thenCorrect() {

        String reverseString = "";
        Collections.reverse(list);
        for (final String item : list) {
            reverseString += item;
        }
        assertEquals(reverseString, StringUtils.reverse(originalString));

        assertEquals("E", list.get(0));
    }

    @Test
    void whenIteratingUsingApacheReverseListIterator_thenCorrect() {

        String reverseString = "";
        final ReverseListIterator listIterator = new ReverseListIterator(list);
        while (listIterator.hasNext()) {
            reverseString += listIterator.next();
        }
        assertEquals(reverseString, StringUtils.reverse(originalString));
    }

    @Test
    void whenIteratingUsingGuava_thenCorrect() {

        String reverseString = "";
        final List<String> reversedList = Lists.reverse(list);
        for (final String item : reversedList) {
            reverseString += item;
        }
        assertEquals(reverseString, StringUtils.reverse(originalString));

        assertEquals("A", list.get(0));
    }
}