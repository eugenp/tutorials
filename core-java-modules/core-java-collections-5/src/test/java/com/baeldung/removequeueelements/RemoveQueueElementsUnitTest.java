package com.baeldung.removequeueelements;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveQueueElementsUnitTest {
    @Test
    public void givenQueueWithEvenAndOddNumbers_whenRemovingEvenNumbers_thenOddNumbersRemain() {
        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> evenElementsQueue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        while (queue.peek() != null) {
            int element = queue.remove();
            if (element % 2 != 0) {
                evenElementsQueue.add(element);
            }
        }

        assertEquals(3, evenElementsQueue.size());
        assertTrue(evenElementsQueue.contains(1));
        assertTrue(evenElementsQueue.contains(3));
        assertTrue(evenElementsQueue.contains(5));
    }

    @Test
    public void givenStringQueue_whenRemovingStringsThatStartWithA_thenStringElementsRemain() {
        Queue<String> queue = new LinkedList<>();
        Queue<String> stringElementsQueue = new LinkedList<>();
        queue.add("Apple");
        queue.add("Banana");
        queue.add("Orange");
        queue.add("Grape");
        queue.add("Mango");


        while (queue.peek() != null) {
            String element = queue.remove();
            if (!element.startsWith("A")) {
                stringElementsQueue.add(element);
            }
        }

        assertEquals(4, stringElementsQueue.size());
        assertTrue(stringElementsQueue.contains("Banana"));
        assertTrue(stringElementsQueue.contains("Orange"));
        assertTrue(stringElementsQueue.contains("Grape"));
        assertTrue(stringElementsQueue.contains("Mango"));
    }

}
