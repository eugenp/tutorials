package com.baeldung.filter

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class TakeTest {

    @Test
    fun `given array of alternating types, when predicating on 'is String', then produce list of array up until predicate is false`() {
        val originalArray = arrayOf("val1", 2, "val3", 4, "val5", 6)
        val actualList = originalArray.takeWhile { it is String }
        val expectedList = listOf("val1")

        assertIterableEquals(expectedList, actualList)
    }

    @Test
    fun `given array of alternating types, when taking 4 items, then produce list of first 4 items`() {
        val originalArray = arrayOf("val1", 2, "val3", 4, "val5", 6)
        val actualList = originalArray.take(4)
        val expectedList = listOf("val1", 2, "val3", 4)

        println(originalArray.drop(4))
        println(actualList)

        assertIterableEquals(expectedList, actualList)
    }

    @Test
    fun `when taking more items than available, then return all elements`() {
        val originalArray = arrayOf(1, 2)
        val actual = originalArray.take(10)
        val expected = listOf(1, 2)

        assertIterableEquals(expected, actual)
    }

}