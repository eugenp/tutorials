package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

class StepTest {

    @Test
    fun testStep() {
        assertEquals(listOf(1, 3, 5, 7, 9), (1..9 step 2).toList())
    }

    @Test
    fun testStepDown() {
        assertEquals(listOf(9, 7, 5, 3, 1), (9 downTo 1 step 2).toList())
    }
}