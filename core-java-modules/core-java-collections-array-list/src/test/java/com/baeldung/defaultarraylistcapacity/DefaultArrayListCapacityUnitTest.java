package com.baeldung.defaultarraylistcapacity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class DefaultArrayListCapacityUnitTest {

    @Test
    void givenEmptyArrayList_whenGetDefaultCapacity_thenReturnZero() throws Exception {

        ArrayList<Integer> myList = new ArrayList<>();
        int defaultCapacity = DefaultArrayListCapacity.getDefaultCapacity(myList);

        assertEquals(0, defaultCapacity);

    }

    @Test
    void givenEmptyArrayList_whenAddItemAndGetDefaultCapacity_thenReturn10() throws Exception {

        ArrayList<String> myList = new ArrayList<>();
        myList.add("ITEM 1");

        int defaultCapacity = DefaultArrayListCapacity.getDefaultCapacity(myList);

        assertEquals(10, defaultCapacity);

    }

}
