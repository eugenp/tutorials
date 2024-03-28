package com.baeldung.algorithms.skiplist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SkipListUnitTest {

    @Test
    public void givenSkipList_WhenInsert_ThenSearchFound() {
        SkipList skipList = new SkipList();

        skipList.insert(3);
        assertTrue(skipList.search(3), "Should find 3");

        assertFalse(skipList.search(99), "Should not find 99");
    }

    @Test
    public void givenSkipList_WhenDeleteElement_ThenRemoveFromList() {
        SkipList skipList = new SkipList();

        skipList.insert(3);
        skipList.insert(7);

        skipList.delete(3);
        assertFalse(skipList.search(3), "3 should have been deleted");

        skipList.delete(99);
        assertTrue(skipList.search(7), "7 should still exist");
    }

}
