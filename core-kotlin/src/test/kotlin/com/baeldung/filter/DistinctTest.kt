package com.baeldung.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class DistinctTest {
    data class SmallClass(val key: String, val num: Int)

    @Test
    fun q() {
        val array = arrayOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 6, 7, 8, 9)
        val result = array.distinct()
        val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        assertTrue { expected == result }
    }

    @Test
    fun w() {

        val originalMap = arrayOf(
                SmallClass("key1", 1),
                SmallClass("key2", 2),
                SmallClass("key3", 3),
                SmallClass("key4", 3),
                SmallClass("er", 9),
                SmallClass("er", 10),
                SmallClass("er", 11))

        val filteredMap = originalMap.distinctBy { it.key }

        val expectedMap = listOf(
                SmallClass("key1", 1),
                SmallClass("key2", 2),
                SmallClass("key3", 3),
                SmallClass("key4", 3),
                SmallClass("er", 9))

        // {key1=1}
        // original map has not changed println(originalMap)
        // {key1=1, key2=2, key3=3}

        assertTrue { expectedMap == filteredMap }
    }

    @Test
    fun e() {
        val array = arrayOf(
                SmallClass("key1", 1),
                SmallClass("key2", 2),
                SmallClass("key3", 3),
                SmallClass("key4", 3),
                SmallClass("er", 9),
                SmallClass("er", 10),
                SmallClass("er", 11),
                SmallClass("er", 11),
                SmallClass("blob", 22),
                SmallClass("dob", 27),
                SmallClass("high", 201_434_314))

        val result = array.distinctBy { Math.floor(it.num / 10.0) }

        // {key1=1}
        // original map has not changed println(array)
        // {key1=1, key2=2, key3=3}

        val expected = listOf(
                SmallClass("key1", 1),
                SmallClass("er", 10),
                SmallClass("blob", 22),
                SmallClass("high", 201_434_314))

        assertTrue { expected == result }
    }
}