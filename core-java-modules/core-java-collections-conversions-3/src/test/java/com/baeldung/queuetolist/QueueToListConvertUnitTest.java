package com.baeldung.queuetolist;

import com.google.common.collect.Lists;
import org.junit.Before;
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
        queue.add("hello");
        queue.add("baeldung");
        queue.add("page");
    }

    @Test
    public void givenAQueue_whenConvertUsingIteration_thenReturnArrayList(){
        List<String> queueList = new ArrayList<>();
        for (String element : queue) {
            queueList.add(element);
        }

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingAddAllMethod_thenReturnArrayList(){
        List<String> queueList = new ArrayList<>();
        queueList.addAll(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));;
    }

    @Test
    public void givenAQueue_whenConvertUsingConstructor_thenReturnLinkedList(){
        LinkedList<String> queueList = new LinkedList<>(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingStream_thenReturnList(){
        List<String> queueList = queue.stream().collect(Collectors.toList());

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingGuava_thenReturnList(){
        List<String> queueList = Lists.newArrayList(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }
}