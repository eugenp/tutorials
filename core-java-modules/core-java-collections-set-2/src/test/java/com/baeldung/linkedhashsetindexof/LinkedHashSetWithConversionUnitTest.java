package com.baeldung.linkedhashsetindexof;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LinkedHashSetWithConversionUnitTest {

    @Test
    public void givenLinkedHashSet_whenConvertToList_thenMaintainsOrder() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        List<String> list = LinkedHashSetWithConversion.convertToList(names);
        assertEquals(3, list.size());
        assertEquals("Mike", list.get(0));
        assertEquals("John", list.get(1));
        assertEquals("Karen", list.get(2));
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexByConversion_thenReturnsCorrectIndex() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        int index = LinkedHashSetWithConversion.getIndexByConversion(names, "John");
        assertEquals(1, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetElementByIndex_thenReturnsCorrectElement() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        String element = LinkedHashSetWithConversion.getElementByIndex(names, 1);
        assertEquals("John", element);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenLinkedHashSet_whenGetElementByInvalidIndex_thenThrowsException() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");

        LinkedHashSetWithConversion.getElementByIndex(names, 10);
    }

    @Test
    public void givenLinkedHashSet_whenConvertToArray_thenMaintainsOrder() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        String[] array = LinkedHashSetWithConversion.convertToArray(names, String.class);
        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals("Mike", array[0]);
        assertEquals("John", array[1]);
        assertEquals("Karen", array[2]);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexOfNonExistentElement_thenReturnsNegativeOne() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");

        int index = LinkedHashSetWithConversion.getIndexByConversion(names, "Alice");
        assertEquals(-1, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexByArray_thenReturnsCorrectIndex() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");
        names.add("Karen");

        int index = LinkedHashSetWithConversion.getIndexByArray(names, "John", String.class);
        assertEquals(1, index);
    }

    @Test
    public void givenLinkedHashSet_whenGetIndexByArrayForNonExistentElement_thenReturnsNegativeOne() {
        Set<String> names = new LinkedHashSet<>();
        names.add("Mike");
        names.add("John");

        int index = LinkedHashSetWithConversion.getIndexByArray(names, "Alice", String.class);
        assertEquals(-1, index);
    }
}

