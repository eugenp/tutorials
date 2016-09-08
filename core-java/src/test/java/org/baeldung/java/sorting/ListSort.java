package org.baeldung.java.sorting;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ListSort {


    @Test
    public void givenList_whenUsingSort_thenSortedList() {
        List<Integer> integers = Arrays.asList(new Integer[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 }), 
          sortedIntegers = Arrays.asList(new Integer[] {1, 5, 7, 66, 88, 89, 123, 200, 255});

        Collections.sort(integers);

        assertTrue(Arrays.equals(integers.toArray(), sortedIntegers.toArray()));
    }



}
