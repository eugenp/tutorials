package com.baeldung.queuetolist;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class QueueToListConvertUnitTest {

    private Queue<String> queue;

    @BeforeEach
    public void beforeEach() {
        queue = new LinkedList<>();
        queue.add("AA");
        queue.add("BB");
        queue.add("CC");
    }

    @Test
    public void givenAQueue_whenConvertUsingConstructor_thenReturnArrayList() {
        List<String> list = new ArrayList<>(queue);

        assertNotNull(list);
        assertEquals(queue.size(), list.size());
        assertTrue(list.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingAddAllMethod_thenReturnList() {
        List<String> list = new ArrayList<>();
        list.addAll(queue);

        assertNotNull(list);
        assertEquals(queue.size(), list.size());
        assertTrue(list.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingConstructor_thenReturnLinkedList() {
        LinkedList<String> list = new LinkedList<>(queue);

        assertNotNull(list);
        assertEquals(queue.size(), list.size());
        assertTrue(list.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingStream_thenReturnList() {
        List<String> list = queue.stream()
            .collect(Collectors.toList());

        assertNotNull(list);
        assertEquals(queue.size(), list.size());
        assertTrue(list.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingGuava_thenReturnList() {
        List<String> list = Lists.newArrayList(queue);

        assertNotNull(list);
        assertEquals(queue.size(), list.size());
        assertTrue(list.containsAll(queue));
    }
}