package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

class FilterTest {

    val r = 1..10

    @Test
    fun filterTest() {
        assertEquals(listOf(2, 4, 6, 8, 10), r.filter { it -> it % 2 == 0 }.toList())
    }

    @Test
    fun mapTest() {
        assertEquals(listOf(1, 4, 9, 16, 25, 36, 49, 64, 81, 100), r.map { it -> it * it }.toList())
    }

    @Test
    fun reduceTest() {
        assertEquals(55, r.reduce { a, b -> a + b })
    }
}