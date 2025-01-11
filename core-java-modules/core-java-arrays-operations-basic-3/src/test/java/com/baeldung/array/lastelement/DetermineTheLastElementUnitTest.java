package com.baeldung.array.lastelement;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetermineTheLastElementUnitTest {
    private static final String[] MY_ARRAY = { "aa", "bb", "cc", "aa", "bb", "cc" };
    private static final String EXPECTED_RESULT = "aa->bb->cc->aa->bb->cc[END]";

    private static final int[] INT_ARRAY = { 1, 2, 3, 1, 2, 3 };
    private static final String EXPECTED_INT_ARRAY_RESULT = "1->2->3->1->2->3[END]";

    private static final char[] CHAR_ARRAY = { 'a', 'b', 'c', 'a', 'b', 'c' };
    private static final String EXPECTED_CHAR_ARRAY_RESULT = "a->b->c->a->b->c[END]";

    @Test
    void whenForLoopAndCheckingIndex_thenCorrect() {
        int lastIndex = MY_ARRAY.length - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MY_ARRAY.length; i++) {
            sb.append(MY_ARRAY[i]);
            if (i == lastIndex) {
                sb.append("[END]");
            } else {
                sb.append("->");
            }
        }
        assertEquals(EXPECTED_RESULT, sb.toString());
    }


    @Test
    void whenUsingForEachLoopWithAnExternalCounter_thenCorrect() {
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        for (String element : MY_ARRAY) {
            sb.append(element);
            if (++counter == MY_ARRAY.length) {
                sb.append("[END]");
            } else {
                sb.append("->");
            }
        }
        assertEquals(EXPECTED_RESULT, sb.toString());
    }

    @Test
    void whenUsingIterableAndIterator_thenCorrect() {
        Iterator<String> it = Arrays.asList(MY_ARRAY).iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append("->");
            } else {
                sb.append("[END]");
            }
        }
        assertEquals(EXPECTED_RESULT, sb.toString());
    }

    @Test
    void whenUsingStreamAndIterator_thenCorrect() {
        Iterator<String> it = Arrays.stream(MY_ARRAY).iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append("->");
            } else {
                sb.append("[END]");
            }
        }
        assertEquals(EXPECTED_RESULT, sb.toString());
    }

    @Test
    void whenUsingIteratorOnIntArray_thenCorrect() {
        Iterator<Integer> it = IntStream.of(INT_ARRAY).iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append("->");
            } else {
                sb.append("[END]");
            }
        }
        assertEquals(EXPECTED_INT_ARRAY_RESULT, sb.toString());
    }

    @Test
    void whenUsingCustomIteratorOnCharArray_thenCorrect() {
        Iterator<Character> it = CharArrayIterator.of(CHAR_ARRAY);
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append("->");
            } else {
                sb.append("[END]");
            }
        }
        assertEquals(EXPECTED_CHAR_ARRAY_RESULT, sb.toString());
    }
}

class CharArrayIterator implements Iterator<Character> {
    private final char[] theArray;
    private int currentIndex = 0;

    public static CharArrayIterator of(char[] array) {
        return new CharArrayIterator(array);
    }

    private CharArrayIterator(char[] array) {
        theArray = array;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < theArray.length;
    }

    @Override
    public Character next() {
        return theArray[currentIndex++];
    }
}