package com.baeldung.array.removeallelements;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class RemoveAllElementsFromStringArrayUnitTest {

    private static final String[] SIX_NULL_ARRAY = new String[] { null, null, null, null, null, null };

    @Test
    void whenReassignNonFinalArray_thenCorrect() {
        String[] myArray1 = new String[] { "Java", "Kotlin", "Ruby", "Go", "C#", "C++" };
        myArray1 = new String[0];
        assertEquals(0, myArray1.length);

        String[] myArray2 = new String[] { "Arch Linux", "Debian", "CentOS", "Gentoo", "Fedora", "Redhat" };
        myArray2 = new String[myArray2.length];
        assertArrayEquals(SIX_NULL_ARRAY, myArray2);
    }

    @Test
    void whenResettingAllElementsToNull_thenCorrect() {
        final String[] myArray = new String[] { "A", "B", "C", "D", "E", "F" };
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = null;
        }
        assertArrayEquals(SIX_NULL_ARRAY, myArray);
    }

    @Test
    void whenUsingArraysFill_thenCorrect() {
        final String[] myArray = new String[] { "a", "b", "c", "d", "e", "f" };
        Arrays.fill(myArray, null);
        assertArrayEquals(SIX_NULL_ARRAY, myArray);
    }
}