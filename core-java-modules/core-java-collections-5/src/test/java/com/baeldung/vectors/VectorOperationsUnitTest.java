package com.baeldung.vectors;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

public class VectorOperationsUnitTest {

    private static Vector<String> getVector() {
        Vector<String> vector = new Vector<String>();
        vector.add("Today");
        vector.add("is");
        vector.add("a");
        vector.add("great");
        vector.add("day!");

        return vector;
    }

    @Test
    public void givenAVector_whenAddElementsUsingAddMethod_thenElementsGetAddedAtEnd() {
        Vector<String> vector = getVector();
        vector.add("Hello");
        assertEquals(6, vector.size());
    }

    @Test
    public void givenAVector_whenUpdateElementAtIndex_thenElementAtIndexGetsUpdated() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());
        assertEquals("great", vector.get(3));
        vector.set(3, "good");
        assertEquals("good", vector.get(3));
    }

    @Test
    public void givenAVector_whenRemoveAnElement_thenElementGetsRemovedFromTheVector() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());

        // remove a specific element
        vector.remove("a");
        assertEquals(4, vector.size());

        // remove at specific index
        vector.remove(2);
        assertEquals("day!", vector.get(2));
        assertEquals(3, vector.size());
        
        assertEquals(false, vector.remove("SomethingThatDoesn'tExist"));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void givenAVector_whenIndexIsBeyondRange_thenRemoveMethodThrowsArrayIndexOutOfBoundsException() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());
        vector.remove(10);
    }

    @Test
    public void givenAVector_whenGetElementWithinARange_thenGetMethodGetsAnElementFromTheVector() {
        Vector<String> vector = getVector();
        String fourthElement = vector.get(3);
        assertEquals("great", fourthElement);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void givenAVector_whenGetElementBeyondARange_thenGetMethodThrowsArrayIndexOutOfBoundsException() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());
        vector.get(10);
    }
}
