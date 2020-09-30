package com.baeldung.genericarrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GenericListTest {

    @Test
    public void givenAddOneItem_whenGetFirstElement_shouldReturnFirstElement() {
        GenericList<String> list = new GenericList<>();
        list.add("hello");

        assertEquals("hello", list.get(0));
    }

    @Test
    public void givenAddMultipleItems_whenGetFirstElement_shouldReturnFirstElement() {
        GenericList<String> list = new GenericList<>();
        list.add("hello");
        list.add("from");
        list.add("baeldung");

        assertEquals("hello", list.get(0));
    }

    @Test (expected = ListFullException.class)
    public void givenListCapacity_whenAddMoreThanCapacity_shouldThrowException() {
        GenericList<String> list = new GenericList<>();

        for (int i = 0; i < 6; i++) {
            list.add("hello " + i);
        }
    }

    @Test (expected = ListElementDoesNotExistException.class)
    public void givenList_whenGetElementThatDoesNotExist_shouldThrowException() {
        GenericList<Integer> list = new GenericList<>();
        list.add(10);
        list.add(20);

        list.get(2);
    }
}