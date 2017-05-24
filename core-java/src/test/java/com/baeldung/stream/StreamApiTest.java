package com.baeldung.stream;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamApiTest {
    private StreamApi streamApi = new StreamApi();

    @Test
    public void givenList_whenGetLastElementUsingReduce_thenReturnLastElement() {
        List<String> valueList = new ArrayList<String>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");
        Stream<String> stream = valueList.stream();

        String last = streamApi.getLastElementUsingReduce(stream);

        assertEquals("Sean", last);
    }
    
    @Test
    public void givenListAndCount_whenGetLastElementUsingSkip_thenReturnLastElement() {
        List<String> valueList = new ArrayList<String>();
        valueList.add("Joe");
        valueList.add("John");
        valueList.add("Sean");
        Stream<String> stream = valueList.stream();

        String last = streamApi.getLastElementUsingSkip(stream, valueList.size());

        assertEquals("Sean", last);
    }

}
