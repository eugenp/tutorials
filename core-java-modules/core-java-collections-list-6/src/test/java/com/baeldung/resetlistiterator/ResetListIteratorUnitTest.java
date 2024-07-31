package com.baeldung.resetlistiterator;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ResetListIteratorUnitTest {

    private static final List<String> MY_LIST = List.of("A", "B", "C", "D", "E", "F", "G");

    @Test
    void whenRecreateAnListIterator_thenGetTheExpectedResult() {
        ListIterator<String> lit = MY_LIST.listIterator();
        lit.next();
        lit.next();
        lit.next();
        lit.next();

        lit = MY_LIST.listIterator();

        assertFalse(lit.hasPrevious());
        assertEquals("A", lit.next());
    }

    @Test
    void whenBackwardIterationToTheFirst_thenGetTheExpectedResult() {
        ListIterator<String> lit = MY_LIST.listIterator();
        lit.next();
        lit.next();
        lit.next();
        lit.next();


        while (lit.hasPrevious()) {
            lit.previous();
        }

        assertFalse(lit.hasPrevious());
        assertEquals("A", lit.next());
    }

}