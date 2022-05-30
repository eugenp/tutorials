package com.baeldung.algorithms.bucketsort;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class IntegerBucketSorterUnitTest {

    private IntegerBucketSorter sorter;

    @Before
    public void setUp() throws Exception {
        sorter = new IntegerBucketSorter();
    }

    @Test
    public void givenUnsortedList_whenSortedUsingBucketSorter_checkSortingCorrect() {

        List<Integer> unsorted = Arrays.asList(80,50,60,30,20,10,70,0,40,500,600,602,200,15);
        List<Integer> expected = Arrays.asList(0,10,15,20,30,40,50,60,70,80,200,500,600,602);

        List<Integer> actual = sorter.sort(unsorted);

        assertEquals(expected, actual);


    }
}