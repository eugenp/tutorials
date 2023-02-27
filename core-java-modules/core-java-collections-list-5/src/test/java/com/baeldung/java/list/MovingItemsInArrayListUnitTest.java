package com.baeldung.java.list;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class MovingItemsInArrayListUnitTest {

    @Test
    public void givenAList_whenManuallyReordering_thenOneItemMovesPosition() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five"));

        String removed = arrayList.remove(3);
        arrayList.add(2, removed);

        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList("one", "two", "four", "three", "five"));
        assertEquals(expectedResult, arrayList);
    }

    @Test
    public void givenAList_whenUsingSwap_thenItemsSwapPositions() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five"));

        Collections.swap(arrayList, 1, 3);

        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList("one", "four", "three", "two", "five"));
        assertEquals(expectedResult, arrayList);
    }

    @Test
    public void givenAList_whenUsingRotateWithPositiveDistance_thenItemsMoveToTheRight() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five"));

        Collections.rotate(arrayList, 2);

        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList("four", "five", "one", "two", "three"));
        assertEquals(expectedResult, arrayList);
    }

    @Test
    public void givenAList_whenUsingRotateWithNegativeDistance_thenItemsMoveToTheLeft() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five"));

        Collections.rotate(arrayList, -2);

        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList("three", "four", "five", "one", "two"));
        assertEquals(expectedResult, arrayList);
    }
}
