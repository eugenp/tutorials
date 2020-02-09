package com.baeldung.sorting

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SortingExampleKtTest {

    @Test
    fun naturalOrderComparator_ShouldBeAscendingTest() {
        val resultingList = listOf(1, 5, 6, 6, 2, 3, 4).sortedWith(getSimpleComparator())
        assertTrue(listOf(1, 2, 3, 4, 5, 6, 6) == resultingList)
    }
}