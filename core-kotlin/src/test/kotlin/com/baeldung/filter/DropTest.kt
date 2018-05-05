package com.baeldung.filter

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class DropTest {

    @Test
    fun whenDroppingFirstTwoItemsOfArray_thenTwoLess() {
        val array = arrayOf(1, 2, 3, 4)
        val result = array.drop(2)
        val expected = listOf(3, 4)

        assertIterableEquals(expected, result)
    }

    @Test
    fun whenDroppingMoreItemsOfArray_thenEmptyList() {
        val array = arrayOf(1, 2, 3, 4)
        val result = array.drop(5)
        val expected = listOf<Int>()

        assertIterableEquals(expected, result)
    }

    @Test
    fun givenArray_whenDroppingLastElement_thenReturnListWithoutLastElement() {
        val array = arrayOf("1", "2", "3", "4")
        val result = array.dropLast(1)
        val expected = listOf("1", "2", "3")

        assertIterableEquals(expected, result)
    }

    @Test
    fun givenArrayOfFloats_whenDroppingLastUntilPredicateIsFalse_thenReturnSubsetListOfFloats() {
        val array = arrayOf(1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f)
        val result = array.dropLastWhile { it == 1f }
        val expected = listOf(1f, 1f, 1f, 1f, 1f, 2f)

        assertIterableEquals(expected, result)
    }

    @Test
    fun givenList_whenDroppingMoreThanAvailable_thenThrowException() {
        val list = listOf('a', 'e', 'i', 'o', 'u')
        val result = list.drop(6)
        val expected: List<String> = listOf()

        assertIterableEquals(expected, result)
    }

}