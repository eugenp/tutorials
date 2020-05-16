package com.baeldung.operators

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PointTest {

    private val p1 = Point(1, 2)
    private val p2 = Point(2, 3)

    @Test
    fun `We should be able to add two points together using +`() {
        assertEquals(p1 + p2, Point(3, 5))
    }

    @Test
    fun `We shoud be able to subtract one point from another using -`() {
        assertEquals(p1 - p2, Point(-1, -1))
    }

    @Test
    fun `We should be able to multiply two points together with *`() {
        assertEquals(p1 * p2, Point(2, 6))
    }

    @Test
    fun `We should be able to divide one point by another`() {
        assertEquals(p1 / p2, Point(0, 0))
    }

    @Test
    fun `We should be able to scale a point by an integral factor`() {
        assertEquals(p1 * 2, Point(2, 4))
        assertEquals(2 * p1, Point(2, 4))
    }

    @Test
    fun `We should be able to add points to an empty shape`() {
        val line = shape {
            +Point(0, 0)
            +Point(1, 3)
        }

        assertTrue(Point(0, 0) in line.points)
        assertTrue(Point(1, 3) in line.points)
    }
}