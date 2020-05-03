package com.baeldung.collections.transformations

import org.junit.Assert.assertEquals
import org.junit.Test

class MapUnitTest {
    @Test
    fun testMapWithLambda() {
        val input = listOf("one", "two", "three")

        val reversed = input.map { it.reversed() }
        assertEquals(listOf("eno", "owt", "eerht"), reversed)

        val lengths = input.map { it.length }
        assertEquals(listOf(3, 3, 5), lengths)
    }

    @Test
    fun testMapIndexed() {
        val input = listOf(3, 2, 1)
        val result = input.mapIndexed { index, value -> index * value }
        assertEquals(listOf(0, 2, 2), result)
    }

    @Test
    fun testMapNotNull() {
        val input = listOf(1, 2, 3, 4, 5)
        val smallSquares = input.mapNotNull {
            if (it <= 3) {
                it * it
            } else {
                null
            }
        }
        assertEquals(listOf(1, 4, 9), smallSquares)
    }

    @Test
    fun mapMapKeys() {
        val inputs = mapOf("one" to 1, "two" to 2, "three" to 3)

        val uppercases = inputs.mapKeys { it.key.toUpperCase() }
        assertEquals(mapOf("ONE" to 1, "TWO" to 2, "THREE" to 3), uppercases)
    }

    @Test
    fun mapMapValues() {
        val inputs = mapOf("one" to 1, "two" to 2, "three" to 3)

        val squares = inputs.mapValues { it.value * it.value }
        assertEquals(mapOf("one" to 1, "two" to 4, "three" to 9), squares)
    }
}