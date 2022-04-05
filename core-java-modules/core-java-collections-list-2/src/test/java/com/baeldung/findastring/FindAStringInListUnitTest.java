package com.baeldung.findastring;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
public class FindAStringInListUnitTest {

    private static List<String> list = new ArrayList<>();

    static {
        list.add("Jack and Jill");
        list.add("James and Sarah");
        list.add("Sam and Louise");
        list.add("Jack");
        list.add("");
    }

    private static FindAStringInGivenList findAStringInGivenList = new FindAStringInGivenList();

    @Test
    public void givenAString_whenFoundUsingLoopWithRegex_thenReturnList() {
        List matchingElements = findAStringInGivenList.findUsingLoopWithRegex("Jack", list);
        assertEquals(2, matchingElements.size());
        assertEquals("Jack and Jill", matchingElements.get(0));
        assertEquals("Jack", matchingElements.get(1));
    }

    @Test
    public void givenAString_whenFoundUsingLoop_thenReturnList() {
        List matchingElements = findAStringInGivenList.findUsingLoop("Jack", list);
        assertEquals(2, matchingElements.size());
        assertEquals("Jack and Jill", matchingElements.get(0));
        assertEquals("Jack", matchingElements.get(1));
    }


    @Test
    public void givenAString_whenFoundUsingStream_thenReturnList(){
        List matchingElements = findAStringInGivenList.findUsingStream("Jack", list);
        assertEquals(2, matchingElements.size());
        assertEquals("Jack and Jill", matchingElements.get(0));
        assertEquals("Jack", matchingElements.get(1));
    }

    @Test
    public void givenAString_whenFoundUsingCommonsCollection_thenReturnList(){
        List matchingElements = findAStringInGivenList.findUsingCommonsCollection("Jack", list);
        assertEquals(2, matchingElements.size());
        assertEquals("Jack and Jill", matchingElements.get(0));
        assertEquals("Jack", matchingElements.get(1));
    }

    @Test
    public void givenAString_whenFoundUsingGuava_thenReturnList(){
        List matchingElements = findAStringInGivenList.findUsingGuava("Jack", list);
        assertEquals(2, matchingElements.size());
        assertEquals("Jack and Jill", matchingElements.get(0));
        assertEquals("Jack", matchingElements.get(1));
    }

}
