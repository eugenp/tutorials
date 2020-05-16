package com.baeldung.collections.transformations

import org.junit.Assert.assertEquals
import org.junit.Test

class AssociateUnitTest {
    @Test
    fun testToMap() {
        val input = listOf(Pair("one", 1), Pair("two", 2))
        val map = input.toMap()
        assertEquals(mapOf("one" to 1, "two" to 2), map)
    }

    @Test
    fun testAssociateWith() {
        val inputs = listOf("Hi", "there")
        val map = inputs.associateWith { k -> k.length }
        assertEquals(mapOf("Hi" to 2, "there" to 5), map)
    }

    @Test
    fun testAssociateBy() {
        val inputs = listOf("Hi", "there")
        val map = inputs.associateBy { v -> v.length }
        assertEquals(mapOf(2 to "Hi", 5 to "there"), map)
    }

    @Test
    fun testAssociate() {
        val inputs = listOf("Hi", "there")
        val map = inputs.associate { e -> Pair(e.toUpperCase(), e.reversed()) }
        assertEquals(mapOf("HI" to "iH", "THERE" to "ereht"), map)
    }

    @Test
    fun testAssociateByDuplicateKeys() {
        val inputs = listOf("one", "two")
        val map = inputs.associateBy { v -> v.length }
        assertEquals(mapOf(3 to "two"), map)
    }

    @Test
    fun testGroupBy() {
        val inputs = listOf("one", "two", "three")
        val map = inputs.groupBy { v -> v.length }
        assertEquals(mapOf(3 to listOf("one", "two"), 5 to listOf("three")), map)
    }
}