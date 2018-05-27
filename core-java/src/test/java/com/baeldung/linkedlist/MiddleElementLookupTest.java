package com.baeldung.linkedlist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MiddleElementLookupTest {

    @Test
    public void whenFindingMiddle_ThenMiddleFound() {
        String middleFound = MiddleElementLookup.findMiddleElement(createList(5));
        assertEquals("3", middleFound);

        middleFound = MiddleElementLookup.findMiddleElement(createList(4));
        assertEquals("2", middleFound);
    }

    @Test
    public void whenFindingMiddle1PassRecursively_ThenMiddleFound() {
        String middleFound = MiddleElementLookup.findMiddleElement1PassRecursively(createList(5));
        assertEquals("3", middleFound);

        middleFound = MiddleElementLookup.findMiddleElement1PassRecursively(createList(4));
        assertEquals("2", middleFound);
    }

    @Test
    public void whenFindingMiddle1PassIteratively_ThenMiddleFound() {
        String middleFound = MiddleElementLookup.findMiddleElement1PassIteratively(createList(5));
        assertEquals("3", middleFound);

        middleFound = MiddleElementLookup.findMiddleElement1PassIteratively(createList(4));
        assertEquals("2", middleFound);
    }

    @Test
    public void whenListEmptyOrNull_ThenMiddleNull() {
        String middleFound = MiddleElementLookup.findMiddleElement(null);
        assertEquals(null, middleFound);

        middleFound = MiddleElementLookup.findMiddleElement(createList(0));
        assertEquals(null, middleFound);

        middleFound = MiddleElementLookup.findMiddleElement1PassIteratively(null);
        assertEquals(null, middleFound);

        middleFound = MiddleElementLookup.findMiddleElement1PassIteratively(createList(0));
        assertEquals(null, middleFound);

        middleFound = MiddleElementLookup.findMiddleElement1PassRecursively(null);
        assertEquals(null, middleFound);

        middleFound = MiddleElementLookup.findMiddleElement1PassRecursively(createList(0));
        assertEquals(null, middleFound);
    }

    private static LinkedList createList(int n) {
        LinkedList list = new LinkedList();

        for (int i = 1; i <= n; i++) {
            list.add(String.valueOf(i));
        }

        return list;
    }

}
