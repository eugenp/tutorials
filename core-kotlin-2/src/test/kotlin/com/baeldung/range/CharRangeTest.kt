package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

class CharRangeTest {

    @Test
    fun testCharRange() {
        assertEquals(listOf('a', 'b', 'c'), ('a'..'c').toList())
    }

    @Test
    fun testCharDownRange() {
        assertEquals(listOf('c', 'b', 'a'), ('c'.downTo('a')).toList())
    }
}