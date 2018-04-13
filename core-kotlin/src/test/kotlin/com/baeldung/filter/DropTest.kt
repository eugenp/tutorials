package com.baeldung.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class DropTest {

    @Test
    fun whenDroppingFirstTwoItemsOfArray_thenTwoLess() {
        val array = arrayOf(1, 2, 3, 4)
        val result = array.drop(2)
        val expected = listOf(3, 4)

        assertTrue { expected == result }
    }

    @Test
    fun w() {
        val array = arrayOf("1", "2", "3", "4")
        val result = array.dropLast(1)
        val expected = listOf("1", "2", "3")

        assertTrue { expected == result }
    }

    @Test
    fun q() {
        val array = arrayOf(1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f)
        val result = array.dropLastWhile { it == 1f }
        val expected = listOf(1f, 1f, 1f, 1f, 1f, 2f)

        assertTrue { expected == result }
    }
}