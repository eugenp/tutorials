package com.baeldung.filter

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class SliceTest {

    @Test
    fun whenSlicingAnArrayWithDotRange_ThenListEqualsTheSlice() {
        val original = arrayOf(1, 2, 3, 2, 1)
        val actual = original.slice(1..3)
        val expected = listOf(2, 3, 2)

        assertIterableEquals(expected, actual)
    }

    @Test
    fun whenSlicingAnArrayWithDownToRange_thenListMadeUpOfReverseSlice() {
        val original = arrayOf(1, 2, 3, 2, 1)
        val actual = original.slice(3 downTo 0)
        val expected = listOf(2, 3, 2, 1)

        assertIterableEquals(expected, actual)
    }

// From the 1.3 version of Kotlin APIs, slice doesn't return array of nulls but throw IndexOutOfBoundsException
//    @Test
//    fun whenSlicingBeyondTheRangeOfTheArray_thenContainManyNulls() {
//        val original = arrayOf(12, 3, 34, 4)
//        val actual = original.slice(3..8)
//        val expected = listOf(4, null, null, null, null, null)
//
//        assertIterableEquals(expected, actual)
//    }

    @Test
    fun whenSlicingBeyondRangeOfArrayWithStep_thenOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException::class.java) {
            val original = arrayOf(12, 3, 34, 4)
            original.slice(3..8 step 2)
        }
    }

}