package com.baeldung.genericarrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GenericListUnitTest {

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

    @Test
    public void givenCharSequenceList_whenAddArrayOfStrings_shouldAddSuccessfully() {
        GenericList<CharSequence> list = new GenericList<>();
        String[] items = {"item1", "item2"};
        list.addAll(items);

        assertEquals("item1", list.get(0));
        assertEquals("item2", list.get(1));
    }
}