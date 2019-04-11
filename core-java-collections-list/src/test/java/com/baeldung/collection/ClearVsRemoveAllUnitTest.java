package com.baeldung.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests demonstrating differences between ArrayList#clear() and ArrayList#removeAll()
 */
class ClearVsRemoveAllUnitTest {

    /*
     * Tests
     */
    @Test
    void givenArrayListWithElements_whenClear_thenListBecomesEmpty() {
        Collection<Integer> collection = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        collection.clear();

        assertTrue(collection.isEmpty());
    }

    @Test
    void givenTwoArrayListsWithCommonElements_whenRemoveAll_thenFirstListMissElementsFromSecondList() {
        Collection<Integer> firstCollection = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> secondCollection = new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7));

        firstCollection.removeAll(secondCollection);

        assertEquals(Arrays.asList(1, 2), firstCollection);
        assertEquals(Arrays.asList(3, 4, 5, 6, 7), secondCollection);
    }

}
