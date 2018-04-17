package com.baeldung.filter

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class DistinctTest {
    data class SmallClass(val key: String, val num: Int)

    @Test
    fun whenApplyingDistinct_thenReturnListOfNoDuplicateValues() {
        val array = arrayOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 6, 7, 8, 9)
        val result = array.distinct()
        val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        assertIterableEquals(expected, result)
    }

    @Test
    fun givenArrayOfClassObjects_whenApplyingDistinctOnClassProperty_thenReturnListDistinctOnThatValue() {

        val original = arrayOf(
                SmallClass("key1", 1),
                SmallClass("key2", 2),
                SmallClass("key3", 3),
                SmallClass("key4", 3),
                SmallClass("er", 9),
                SmallClass("er", 10),
                SmallClass("er", 11))

        val actual = original.distinctBy { it.key }

        val expected = listOf(
                SmallClass("key1", 1),
                SmallClass("key2", 2),
                SmallClass("key3", 3),
                SmallClass("key4", 3),
                SmallClass("er", 9))


        assertIterableEquals(expected, actual)
    }

    @Test
    fun givenArrayOfClassObjects_whenApplyingComplicatedSelector_thenReturnFirstElementToMatchEachSelectorValue() {
        val array = arrayOf(
                SmallClass("key1", 1),
                SmallClass("key2", 2),
                SmallClass("key3", 3),
                SmallClass("key4", 3),
                SmallClass("er", 9),
                SmallClass("er", 10),
                SmallClass("er", 11),
                SmallClass("er", 11),
                SmallClass("er", 91),
                SmallClass("blob", 22),
                SmallClass("dob", 27),
                SmallClass("high", 201_434_314))

        val actual = array.distinctBy { Math.floor(it.num / 10.0) }

        val expected = listOf(
                SmallClass("key1", 1),
                SmallClass("er", 10),
                SmallClass("er", 91),
                SmallClass("blob", 22),
                SmallClass("high", 201_434_314))

        assertIterableEquals(expected, actual)
    }

}