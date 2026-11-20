package com.baeldung.linkedhashsetindexof;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListAndSetApproachUnitTest {

    @Test
    public void givenListAndSetApproach_whenAddElement_thenElementIsAdded() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        assertTrue(approach.add("Mike"));
        assertTrue(approach.contains("Mike"));
        assertEquals(1, approach.size());
    }

    @Test
    public void givenListAndSetApproach_whenAddDuplicate_thenDuplicateIsNotAdded() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        assertTrue(approach.add("Mike"));
        assertFalse(approach.add("Mike"));
        assertEquals(1, approach.size());
    }

    @Test
    public void givenListAndSetApproach_whenGetIndex_thenReturnsCorrectIndex() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        approach.add("Mike");
        approach.add("John");
        approach.add("Karen");

        assertEquals(0, approach.indexOf("Mike"));
        assertEquals(1, approach.indexOf("John"));
        assertEquals(2, approach.indexOf("Karen"));
    }

    @Test
    public void givenListAndSetApproach_whenGetByIndex_thenReturnsCorrectElement() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        approach.add("Mike");
        approach.add("John");
        approach.add("Karen");

        assertEquals("Mike", approach.get(0));
        assertEquals("John", approach.get(1));
        assertEquals("Karen", approach.get(2));
    }

    @Test
    public void givenListAndSetApproach_whenRemoveElement_thenElementIsRemoved() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        approach.add("Mike");
        approach.add("John");
        approach.add("Karen");

        assertTrue(approach.remove("John"));
        assertFalse(approach.contains("John"));
        assertEquals(2, approach.size());
        assertEquals(0, approach.indexOf("Mike"));
        assertEquals(1, approach.indexOf("Karen"));
    }

    @Test
    public void givenListAndSetApproach_whenRemoveNonExistent_thenReturnsFalse() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        approach.add("Mike");

        assertFalse(approach.remove("John"));
        assertEquals(1, approach.size());
    }

    @Test
    public void givenListAndSetApproach_whenClear_thenBothStructuresAreEmpty() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        approach.add("Mike");
        approach.add("John");

        approach.clear();
        assertTrue(approach.isEmpty());
        assertEquals(0, approach.size());
    }

    @Test
    public void givenListAndSetApproach_whenGetIndexOfNonExistent_thenReturnsNegativeOne() {
        ListAndSetApproach<String> approach = new ListAndSetApproach<>();
        approach.add("Mike");

        assertEquals(-1, approach.indexOf("John"));
    }
}


