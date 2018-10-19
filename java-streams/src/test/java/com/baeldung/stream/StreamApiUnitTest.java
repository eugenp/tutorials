package com.baeldung.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StreamApiUnitTest {

    @Test
    public void givenList_whenGetLastElementUsingReduce_thenReturnLastElement() {
        List<String> valueList = new ArrayList<>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");

        String last = StreamApi.getLastElementUsingReduce(valueList);

        assertEquals("Sean", last);
    }

    @Test
    public void givenInfiniteStream_whenGetInfiniteStreamLastElementUsingReduce_thenReturnLastElement() {
        int last = StreamApi.getInfiniteStreamLastElementUsingReduce();
        assertEquals(19, last);
    }

    @Test
    public void givenListAndCount_whenGetLastElementUsingSkip_thenReturnLastElement() {
        List<String> valueList = new ArrayList<>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");

        String last = StreamApi.getLastElementUsingSkip(valueList);

        assertEquals("Sean", last);
    }

}
