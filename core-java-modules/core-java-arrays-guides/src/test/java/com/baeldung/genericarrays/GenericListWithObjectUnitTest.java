package com.baeldung.genericarrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenericListWithObjectUnitTest {

    @Test
    public void givenAddOneItem_whenGetFirstElement_shouldReturnFirstElement() {
        GenericListWithObject<String> list = new GenericListWithObject<>();
        list.add("hello");

        assertEquals("hello", list.get(0));
    }

    @Test
    public void givenAddMultipleItems_whenGetFirstElement_shouldReturnFirstElement() {
        GenericListWithObject<String> list = new GenericListWithObject<>();
        list.add("hello");
        list.add("from");
        list.add("baeldung");

        assertEquals("hello", list.get(0));
    }

    @Test (expected = ListFullException.class)
    public void givenListCapacity_whenAddMoreThanCapacity_shouldThrowException() {
        GenericListWithObject<String> list = new GenericListWithObject<>();

        for (int i = 0; i < 6; i++) {
            list.add("hello " + i);
        }
    }

    @Test (expected = ListElementDoesNotExistException.class)
    public void givenList_whenGetElementThatDoesNotExist_shouldThrowException() {
        GenericListWithObject<Integer> list = new GenericListWithObject<>();
        list.add(10);
        list.add(20);

        list.get(2);
    }
}