package com.baeldung.genericarrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenericListUsingObjectTest {

    @Test
    public void givenAddOneItem_whenGetFirstElement_shouldReturnFirstElement() {
        GenericListUsingObject<String> list = new GenericListUsingObject<>();
        list.add("hello");

        assertEquals("hello", list.get(0));
    }

    @Test
    public void givenAddMultipleItems_whenGetFirstElement_shouldReturnFirstElement() {
        GenericListUsingObject<String> list = new GenericListUsingObject<>();
        list.add("hello");
        list.add("from");
        list.add("baeldung");

        assertEquals("hello", list.get(0));
    }

    @Test (expected = ListFullException.class)
    public void givenListCapacity_whenAddMoreThanCapacity_shouldThrowException() {
        GenericListUsingObject<String> list = new GenericListUsingObject<>();

        for (int i = 0; i < 6; i++) {
            list.add("hello " + i);
        }
    }

    @Test (expected = ListElementDoesNotExistException.class)
    public void givenList_whenGetElementThatDoesNotExist_shouldThrowException() {
        GenericListUsingObject<Integer> list = new GenericListUsingObject<>();
        list.add(10);
        list.add(20);

        list.get(2);
    }
}