package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

class ReverseRangeTest {

    @Test
    fun reversedTest() {
        assertEquals(listOf(9, 6, 3), (1..9).reversed().step(3).toList())
    }
}