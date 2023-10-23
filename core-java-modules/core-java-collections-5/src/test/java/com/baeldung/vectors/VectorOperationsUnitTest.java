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
    public void givenAVector_addMethodAddsTheElements() {
        Vector<String> vector = getVector();
        vector.add("Hello");
        assertEquals(6, vector.size());
    }

    @Test
    public void givenAVector_updateMethodUpdatesTheVector() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());
        vector.set(3, "good");
        assertEquals("good", vector.get(3));
    }

    @Test
    public void givenAVector_removeMethodRemovesAnElementFromTheVector() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());

        vector.remove("a");
        assertEquals(4, vector.size());

        vector.remove(2);
        assertEquals("day!", vector.get(2));
        assertEquals(3, vector.size());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void givenAVector_removeMethodThrowsArrayIndexOutOfBoundsException_IfIndexIsBeyondRange() {
        Vector<String> vector = getVector();
        vector.remove(10);
    }

    @Test
    public void givenAVector_getMethodGetsAnElementFromTheVector() {
        Vector<String> vector = getVector();
        String fourthElement = vector.get(3);
        assertEquals("great", fourthElement);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void givenAVector_getMethodThrowsArrayIndexOutOfBoundsException_IfIndexIsBeyondRange() {
        Vector<String> vector = getVector();
        vector.get(10);
    }
}
