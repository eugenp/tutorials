package com.baeldung.linkedhashsetindexof;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class IndexAwareSetWithTwoMapsUnitTest {

    @Test
    public void givenIndexAwareSet_whenAddElement_thenElementIsAdded() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        assertTrue(set.add("Mike"));
        assertEquals(0, set.indexOf("Mike"));
        assertEquals("Mike", set.get(0));
    }

    @Test
    public void givenIndexAwareSet_whenAddDuplicate_thenDuplicateIsNotAdded() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        assertTrue(set.add("Mike"));
        assertFalse(set.add("Mike"));
        assertEquals(0, set.indexOf("Mike"));
    }

    @Test
    public void givenIndexAwareSet_whenAddMultipleElements_thenIndicesAreCorrect() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");

        assertEquals(0, set.indexOf("Mike"));
        assertEquals(1, set.indexOf("John"));
        assertEquals(2, set.indexOf("Karen"));
    }

    @Test
    public void givenIndexAwareSet_whenGetByIndex_thenReturnsCorrectElement() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");

        assertEquals("Mike", set.get(0));
        assertEquals("John", set.get(1));
        assertEquals("Karen", set.get(2));
    }

    @Test
    public void givenIndexAwareSet_whenRemoveElement_thenElementIsRemoved() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");

        assertTrue(set.remove("John"));
        assertEquals(-1, set.indexOf("John"));
        assertEquals(0, set.indexOf("Mike"));
        assertEquals(1, set.indexOf("Karen"));
    }

    @Test
    public void givenIndexAwareSet_whenRemoveElement_thenIndicesAreAdjusted() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");
        set.add("Alice");

        set.remove("John");
        
        assertEquals("Mike", set.get(0));
        assertEquals("Karen", set.get(1));
        assertEquals("Alice", set.get(2));
        assertEquals(0, set.indexOf("Mike"));
        assertEquals(1, set.indexOf("Karen"));
        assertEquals(2, set.indexOf("Alice"));
    }

    @Test
    public void givenIndexAwareSet_whenRemoveFirstElement_thenIndicesAreAdjusted() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");

        set.remove("Mike");
        
        assertEquals("John", set.get(0));
        assertEquals("Karen", set.get(1));
        assertEquals(0, set.indexOf("John"));
        assertEquals(1, set.indexOf("Karen"));
    }

    @Test
    public void givenIndexAwareSet_whenRemoveLastElement_thenIndicesAreAdjusted() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");

        set.remove("Karen");
        
        assertEquals("Mike", set.get(0));
        assertEquals("John", set.get(1));
        assertEquals(0, set.indexOf("Mike"));
        assertEquals(1, set.indexOf("John"));
    }

    @Test
    public void givenIndexAwareSet_whenRemoveNonExistent_thenReturnsFalse() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");

        assertFalse(set.remove("John"));
        assertEquals(0, set.indexOf("Mike"));
        assertEquals("Mike", set.get(0));
    }

    @Test
    public void givenIndexAwareSet_whenGetIndexOfNonExistent_thenReturnsNegativeOne() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");

        assertEquals(-1, set.indexOf("John"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenIndexAwareSet_whenGetByInvalidIndex_thenThrowsException() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");

        set.get(10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenIndexAwareSet_whenGetByNegativeIndex_thenThrowsException() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");

        set.get(-1);
    }

    @Test
    public void givenIndexAwareSet_whenRemoveAllElements_thenCanAddAgain() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        
        set.remove("Mike");
        set.remove("John");
        
        assertTrue(set.add("Karen"));
        assertEquals(0, set.indexOf("Karen"));
        assertEquals("Karen", set.get(0));
    }

    @Test
    public void givenIndexAwareSet_whenMultipleRemovals_thenIndicesRemainCorrect() {
        IndexAwareSetWithTwoMaps<String> set = new IndexAwareSetWithTwoMaps<>();
        set.add("Mike");
        set.add("John");
        set.add("Karen");
        set.add("Alice");
        set.add("Bob");

        set.remove("John");
        set.remove("Alice");
        
        assertEquals("Mike", set.get(0));
        assertEquals("Karen", set.get(1));
        assertEquals("Bob", set.get(2));
        assertEquals(0, set.indexOf("Mike"));
        assertEquals(1, set.indexOf("Karen"));
        assertEquals(2, set.indexOf("Bob"));
    }
}

