package com.baeldung.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class SliceTest {

    @Test
    fun q() {
        val original = arrayOf(1, 2, 3, 2, 1)
        val filteredList1 = original.slice(1..3)
        val filteredList2 = original.slice(3 downTo 0)
        val expectedList1 = listOf(2, 3, 2)
        val expectedList2 = listOf(2, 3, 2, 1)

        assertTrue { expectedList1 == filteredList1 }
        assertTrue { expectedList2 == filteredList2 }
    }

}