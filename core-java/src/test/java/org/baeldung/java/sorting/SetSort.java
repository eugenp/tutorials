package org.baeldung.java.sorting;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class SetSort {


    @Test
    public void givenSet_whenUsingSort_thenSortedSet() {
        HashSet<Integer> integers = new HashSet<>(Arrays.asList(new Integer[] 
            { 5, 1, 89, 255, 7, 88, 200, 123, 66 })),
              sortedIntegers = new HashSet<>(Arrays.asList(new Integer[] 
                {1, 5, 7, 66, 88, 89, 123, 200, 255}));
        
        
        ArrayList<Integer> list = new ArrayList<Integer>(integers);
        Collections.sort(list, (i1, i2) -> {
            return i2 - i1;
        });
        integers = new HashSet<>(list);
        
        assertTrue(Arrays.equals(integers.toArray(), sortedIntegers.toArray()));
    }



}
