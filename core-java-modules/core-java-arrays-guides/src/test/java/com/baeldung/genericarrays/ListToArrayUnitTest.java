package com.baeldung.genericarrays;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListToArrayUnitTest {

    @Test
    public void givenListOfItems_whenToArray_thenReturnArrayOfItems() {
        List<String> items = new LinkedList<>();
        items.add("first item");
        items.add("second item");

        String[] itemsAsArray = items.toArray(new String[0]);

        assertEquals("first item", itemsAsArray[0]);
        assertEquals("second item", itemsAsArray[1]);
    }
}
