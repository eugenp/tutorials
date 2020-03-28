package com.baeldung.collections.transformations

import org.junit.Assert.assertEquals
import org.junit.Test

class FilterUnitTest {
    @Test
    fun testFilterWithLambda() {
        val input = listOf(1, 2, 3, 4, 5)
        val filtered = input.filter { it <= 3 }
        assertEquals(listOf(1, 2, 3), filtered)
    }

    @Test
    fun testFilterWithMethodReference() {
        val input = listOf(1, 2, 3, 4, 5)
        val filtered = input.filter(this::isSmall)
        assertEquals(listOf(1, 2, 3), filtered)
    }

    @Test
    fun testFilterNotWithMethodReference() {
        val input = listOf(1, 2, 3, 4, 5)
        val filtered = input.filterNot(this::isSmall)
        assertEquals(listOf(4, 5), filtered)
    }

    @Test
    fun testFilterIndexed() {
        val input = listOf(5, 4, 3, 2, 1)
        val filtered = input.filterIndexed { index, element -> index < 3 }
        assertEquals(listOf(5, 4, 3), filtered)
    }

    @Test
    fun testFilterNotNull() {
        val nullable: List<String?> = listOf("Hello", null, "World")
        val nonnull: List<String> = nullable.filterNotNull()
        assertEquals(listOf("Hello", "World"), nonnull)
    }

    private fun isSmall(i: Int) = i <= 3
}