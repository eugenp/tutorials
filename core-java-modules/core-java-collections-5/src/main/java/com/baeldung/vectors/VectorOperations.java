package com.baeldung.vectors;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class VectorOperations {

    public static void main(String[] args) {
        addElementsToTheVector();
        updateVector();
        removeElements();
        iterate();
        getElement();
        iterateUsingForEach();
    }

    private static Vector<String> getVector() {
        Vector<String> vector = new Vector<String>();
        vector.add("Today");
        vector.add("is");
        vector.add("a");
        vector.add("great");
        vector.add("day!");

        return vector;
    }

    private static void iterateUsingForEach() {
        Vector<String> vector = getVector();
        vector.forEach(System.out::println);
    }

    private static void getElement() {
        Vector<String> vector = getVector();
        String fourthElement = vector.get(3);
        assertEquals("great", fourthElement);
    }

    private static void iterate() {
        Vector<String> vector = getVector();
        for (String string : vector) {
            System.out.println(string);
        }
    }

    private static void removeElements() {
        Vector<String> vector = getVector();
        assertEquals(5, vector.size());
        vector.remove("a");
        assertEquals(4, vector.size());

        vector.remove(2);
        assertEquals(3, vector.size());
        assertEquals("day!", vector.get(2));

        vector.removeAllElements();
        assertEquals(0, vector.size());
    }

    private static void updateVector() {
        Vector<Integer> vector = new Vector<Integer>();
        vector.add(1);
        vector.add(2);
        vector.add(3);

        assertEquals(3, vector.size());
        vector.set(1, 4);
        assertEquals(4, (int) vector.get(1));
    }

    private static void addElementsToTheVector() {
        Vector<String> vector = getVector();
        vector.add(2, "not"); // add number 3 at index 2
        assertEquals("not", vector.get(2));

        ArrayList<String> words = new ArrayList<>(Arrays.asList("Baeldung", "is", "cool!"));
        vector.addAll(words);

        assertEquals(9, vector.size());
        assertEquals("cool!", vector.get(8));
    }
}
