package com.baeldung.list.arraylist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests demonstrating differences between ArrayList#clear() and ArrayList#removeAll()
 */
class ClearVsRemoveAllTest {

    /*
     * Tests
     */
    @Test
    void givenArrayListWithElements_whenClear_thenListBecomesEmpty() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        list.clear();

        assertTrue(list.isEmpty());
    }

    @Test
    void givenTwoArrayListsWithCommonElements_whenRemoveAll_thenFirstListMissElementsFromSecondList() {
        ArrayList<Integer> firstList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> secondList = new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7));

        firstList.removeAll(secondList);

        assertEquals(Arrays.asList(1, 2), firstList);
    }

}
