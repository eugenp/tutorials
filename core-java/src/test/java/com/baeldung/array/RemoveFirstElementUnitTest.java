package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RemoveFirstElementUnitTest {

    @Test
    public void givenStringArray_whenRemovingFirstElement_thenArrayIsSmallerAndElementRemoved() {
        String[] stringArray = {"foo", "bar", "baz"};

        String[] modifiedArray = Arrays.copyOfRange(stringArray, 1, stringArray.length);

        Assert.assertEquals(2, modifiedArray.length);
        Assert.assertEquals("bar", modifiedArray[0]);
    }

    @Test
    public void givenArrayList_whenRemovingFirstElement_thenListSmallerAndElementRemoved() {
        List<String> stringList = new ArrayList<>(Arrays.asList("foo", "bar", "baz"));
        stringList.remove(0);

        Assert.assertEquals(2, stringList.size());
        Assert.assertEquals("bar", stringList.get(0));
    }

    @Test
    public void givenLinkedList_whenRemovingFirstElement_thenListSmallerAndElementRemoved() {
        List<String> stringList = new LinkedList<>(Arrays.asList("foo", "bar", "baz"));
        stringList.remove(0);

        Assert.assertEquals(2, stringList.size());
        Assert.assertEquals("bar", stringList.get(0));
    }

}
