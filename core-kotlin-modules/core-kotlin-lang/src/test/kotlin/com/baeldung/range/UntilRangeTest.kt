package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

class UntilRangeTest {

    @Test
    fun testUntil() {
        assertEquals(listOf(1, 2, 3, 4), (1 until 5).toList())
    }
}