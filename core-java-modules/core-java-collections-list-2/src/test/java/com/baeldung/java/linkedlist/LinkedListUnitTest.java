package com.baeldung.java.linkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class LinkedListUnitTest {


    @Test
    public void givenArray_whenConvertedUsingAsList_thenLinkedListContainsSameElements() {
        String[] array = {"apple", "banana", "cherry", "date"};
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList(array));
        assertEquals(Arrays.asList(array), linkedList);
    }

    @Test
    public void givenArray_whenConvertedUsingAddAll_thenLinkedListContainsSameElements() {
        String[] array = {"apple", "banana", "cherry", "date"};
        LinkedList<String> linkedList = new LinkedList<>();
        Collections.addAll(linkedList, array);
        assertEquals(Arrays.asList(array), linkedList);
    }

}
