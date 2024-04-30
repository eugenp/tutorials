package com.baeldung.queuetolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import queuetolist.QueueToListConvert;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class QueueToListConvertUnitTest {

    private Queue<String> queue;

    @BeforeEach
    public void beforeEach() {
        queue = new LinkedList<>();
        queue.add("AA");
        queue.add("BB");
        queue.add("CC");
        queue.add("DD");
    }

    @Test
    public void givenAQueue_whenConvertUsingConstructor_thenReturnArrayList(){
        List<String> queueList = QueueToListConvert.convertUsingArrayListConstructor(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingAddAllMethod_thenReturnList(){
        List<String> queueList = QueueToListConvert.convertUsingAddAllMethod(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingConstructor_thenReturnLinkedList(){
        LinkedList<String> queueList = QueueToListConvert.convertUsingLinkedListConstructor(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingStream_thenReturnList(){
        List<String> queueList = QueueToListConvert.convertUsingStream(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }

    @Test
    public void givenAQueue_whenConvertUsingGuava_thenReturnList(){
        List<String> queueList = QueueToListConvert.convertUsingGuava(queue);

        assertNotNull(queueList);
        assertEquals(queue.size(), queueList.size());
        assertTrue(queueList.containsAll(queue));
    }
}