package com.baeldung.collections.transformations

import org.junit.Assert.assertEquals
import org.junit.Test

class ZipUnitTest {
    @Test
    fun testZip() {
        val left = listOf("one", "two", "three")
        val right = listOf(1, 2, 3)
        val zipped = left.zip(right)
        assertEquals (listOf(Pair("one", 1), Pair("two", 2), Pair("three", 3)), zipped)
    }

    @Test
    fun testZipShort() {
        val left = listOf("one", "two")
        val right = listOf(1, 2, 3)
        val zipped = left.zip(right)
        assertEquals (listOf(Pair("one", 1), Pair("two", 2)), zipped)
    }

    @Test
    fun testUnzip() {
        val left = listOf("one", "two", "three")
        val right = listOf(1, 2, 3)
        val zipped = left.zip(right)

        val (newLeft, newRight) = zipped.unzip()
        assertEquals(left, newLeft)
        assertEquals(right, newRight)
    }

}