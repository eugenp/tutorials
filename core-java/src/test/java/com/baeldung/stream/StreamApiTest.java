package com.baeldung.stream;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StreamApiTest {
    private StreamApi streamApi = new StreamApi();

    @Test
    public void givenList_whenGetLastElementUsingReduce_thenReturnLastElement() {
        List<String> valueList = new ArrayList<String>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");

        String last = streamApi.getLastElementUsingReduce(valueList);

        assertEquals("Sean", last);
    }
    
    @Test
    public void givenInfiniteStream_whenGetInfiniteStreamLastElementUsingReduce_thenReturnLastElement() {
        Integer last = streamApi.getInfiniteStreamLastElementUsingReduce();
        assertEquals(new Integer(19), last);
    }
    
    @Test
    public void givenListAndCount_whenGetLastElementUsingSkip_thenReturnLastElement() {
        List<String> valueList = new ArrayList<String>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");

        String last = streamApi.getLastElementUsingSkip(valueList);

        assertEquals("Sean", last);
    }

}
