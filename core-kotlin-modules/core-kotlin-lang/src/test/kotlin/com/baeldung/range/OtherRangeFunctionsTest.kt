package com.baeldung.range

import org.junit.Test
import kotlin.test.assertEquals

class OtherRangeFunctionsTest {

    val r = 1..20
    val repeated = listOf(1, 1, 2, 4, 4, 6, 10)

    @Test
    fun testMin() {
        assertEquals(1, r.min())
    }

    @Test
    fun testMax() {
        assertEquals(20, r.max())
    }

    @Test
    fun testSum() {
        assertEquals(210, r.sum())
    }

    @Test
    fun testAverage() {
        assertEquals(10.5, r.average())
    }

    @Test
    fun testCount() {
        assertEquals(20, r.count())
    }

    @Test
    fun testDistinct() {
        assertEquals(listOf(1, 2, 4, 6, 10), repeated.distinct())
    }
}