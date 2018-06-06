package com.baeldung.linkedlist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MiddleElementLookupUnitTest {

    @Test
    public void whenFindingMiddle_thenMiddleFound() {
        String middle = MiddleElementLookup.findMiddleElement(createList(5).head());
        assertEquals("3", middle);

        middle = MiddleElementLookup.findMiddleElement(createList(4).head());
        assertEquals("2", middle);
    }

    @Test
    public void whenFindingMiddle1PassRecursively_thenMiddleFound() {
        String middle = MiddleElementLookup.findMiddleElement1PassRecursively(createList(5).head());
        assertEquals("3", middle);

        middle = MiddleElementLookup.findMiddleElement1PassRecursively(createList(4).head());
        assertEquals("2", middle);
    }

    @Test
    public void whenFindingMiddle1PassIteratively_thenMiddleFound() {
        String middle = MiddleElementLookup.findMiddleElement1PassIteratively(createList(5).head());
        assertEquals("3", middle);

        middle = MiddleElementLookup.findMiddleElement1PassIteratively(createList(4).head());
        assertEquals("2", middle);
    }

    @Test
    public void whenListEmptyOrNull_thenMiddleNull() {
        String middle = MiddleElementLookup.findMiddleElement(null);
        assertEquals(null, middle);

        middle = MiddleElementLookup.findMiddleElement1PassIteratively(null);
        assertEquals(null, middle);

        middle = MiddleElementLookup.findMiddleElement1PassRecursively(null);
        assertEquals(null, middle);
    }

    private static LinkedList createList(int n) {
        LinkedList list = new LinkedList();

        for (int i = 1; i <= n; i++) {
            list.add(String.valueOf(i));
        }

        return list;
    }

}
