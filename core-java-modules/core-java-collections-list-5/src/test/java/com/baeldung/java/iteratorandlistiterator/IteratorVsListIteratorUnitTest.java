package com.baeldung.java.iteratorandlistiterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class IteratorVsListIteratorUnitTest {

    @Test
    void whenUsingIterator_thenWeCanTraverseForwardsAndRemoveElements() {
        List<String> inputList = Lists.newArrayList("1", "2", "3", "4", "5");
        Iterator<String> it = inputList.iterator();
        while (it.hasNext()) {
            String e = it.next();
            if ("3".equals(e) || "5".equals(e)) {
                it.remove();
            }
        }

        assertEquals(Lists.newArrayList("1", "2", "4"), inputList);

    }

    @Test
    void whenUsingListIteratorAlternatingNextAndPrevious_thenAlwaysGetTheSameElement() {
        List<String> inputList = Lists.newArrayList("1", "2", "3", "4", "5");
        ListIterator<String> lit = inputList.listIterator(); // ^ 1 2 3 4 5
        lit.next(); // 1 ^ 2 3 4 5
        lit.next(); // 1 2 ^ 3 4 5

        for (int i = 1; i <= 100; i++) {
            assertEquals("2", lit.previous());
            assertEquals("2", lit.next());
        }

    }

    @Test
    void whenUsingListIterator_thenRetrieveElementInForwardAndBackwardDirection() {
        List<String> inputList = Lists.newArrayList("1", "2", "3", "4", "5");
        ListIterator<String> lit = inputList.listIterator(); // ^ 1 2 3 4 5

        assertFalse(lit.hasPrevious()); // lit is at the beginning of the list
        assertEquals(-1, lit.previousIndex());

        assertEquals("1", lit.next()); // after next(): 1 ^ 2 3 4 5
        assertEquals("2", lit.next()); // after next(): 1 2 ^ 3 4 5
        assertEquals("3", lit.next()); // after next(): 1 2 3 ^ 4 5

        assertTrue(lit.hasPrevious());
        assertEquals(2, lit.previousIndex());
        assertEquals("3", lit.previous()); // after previous(): 1 2 ^ 3 4 5

        assertTrue(lit.hasPrevious());
        assertEquals(1, lit.previousIndex());
        assertEquals("2", lit.previous()); // after previous(): 1 ^ 2 3 4 5

        assertTrue(lit.hasPrevious());
        assertEquals(0, lit.previousIndex());
        assertEquals("1", lit.previous()); // after previous(): ^ 1 2 3 4 5
    }

    @Test
    void whenUsingSetElement_thenGetExpectedResult() {
        List<String> inputList = Lists.newArrayList("1", "2", "3", "4", "5");
        ListIterator<String> lit = inputList.listIterator(1); // ^ 1 2 3 4 5
        lit.next(); // 1 ^ 2 3 4 5

        assertEquals("3", lit.next()); // 1 2 ^ 3 4 5
        lit.set("X");
        assertEquals(Lists.newArrayList("1", "2", "X", "4", "5"), inputList);

        assertEquals("X", lit.previous()); // 1 2 ^ X 4 5

        assertEquals("2", lit.previous()); // 1 ^ 2 X 4 5
        lit.set("Y");
        assertEquals(Lists.newArrayList("1", "Y", "X", "4", "5"), inputList);
    }

    @Test
    void whenUsingAddElement_thenGetExpectedResult() {
        List<String> inputList = Lists.newArrayList("1", "2", "3", "4", "5");
        ListIterator<String> lit = inputList.listIterator(); // ^ 1 2 3 4 5
        lit.next(); // 1 ^ 2 3 4 5
        lit.next(); // 1 2 ^ 3 4 5
        lit.next(); // 1 2 3 ^ 4 5

        lit.add("X"); // 1 2 3 X ^ 4 5
        assertEquals("4", lit.next()); // 1 2 3 X 4 ^ 5

        lit.previous(); // 1 2 3 X ^ 4 5
        lit.previous(); // 1 2 3 ^ X 4 5
        lit.previous(); // 1 2 ^ 3 X 4 5
        lit.add("Y");   // 1 2 Y ^ 3 X 4 5

        assertEquals("Y", lit.previous());

        assertEquals(Lists.newArrayList("1", "2", "Y", "3", "X", "4", "5"), inputList);
    }
}