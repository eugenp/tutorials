package com.baeldung.toarraymethod;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConvertingHashSetToArrayUnitTest {

    @Test
    public void givenStringHashSet_whenConvertedToArray_thenArrayContainsStringElements() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("Apple");
        stringSet.add("Banana");
        stringSet.add("Cherry");

        // Convert the HashSet of Strings to an array of Strings
        String[] stringArray = stringSet.toArray(new String[0]);

        // Test that the array is of the correct length
        assertEquals(3, stringArray.length);

        for (String str : stringArray) {
            assertTrue(stringSet.contains(str));
        }
    }

    @Test
    public void givenIntegerHashSet_whenConvertedToArray_thenArrayContainsIntegerElements() {
        HashSet<Integer> integerSet = new HashSet<>();
        integerSet.add(5);
        integerSet.add(10);
        integerSet.add(15);

        // Convert the HashSet of Integers to an array of Integers
        Integer[] integerArray = integerSet.toArray(new Integer[0]);

        // Test that the array is of the correct length
        assertEquals(3, integerArray.length);

        for (Integer num : integerArray) {
            assertTrue(integerSet.contains(num));
        }

        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertTrue(integerSet.contains(15));
    }
}
