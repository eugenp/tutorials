package com.baeldung.java.list;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class ReverseIteratorUnitTest {

    private final ReverseIterator reverseIterator = new ReverseIterator();

    private List<String> list;

    @BeforeEach
    void setUp() {

        list = Lists.newArrayList("A", "B", "C", "D", "E");
    }

    @Test
    void whenIteratingUsingListIterator_thenCorrect() {

        reverseIterator.iterateUsingListIterator(list);
    }

    @Test
    void whenIteratingUsingCollections_thenCorrect() {

        reverseIterator.iterateUsingCollections(list);
    }

    @Test
    void whenIteratingUsingApacheReverseListIterator_thenCorrect() {

        reverseIterator.iterateUsingApacheReverseListIterator(list);
    }

    @Test
    void whenIteratingUsingGuava_thenCorrect() {

        reverseIterator.iterateUsingGuava(list);
    }
}