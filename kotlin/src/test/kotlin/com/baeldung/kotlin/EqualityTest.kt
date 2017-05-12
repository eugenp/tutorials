package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EqualityTest {

    // Checks referential equality
    @Test
    fun givenTwoIntegers_whenCheckReference_thenEqualReference() {
        val a = Integer(10)
        val b = Integer(10)

        assertFalse(a === b)
    }

    // Checks structural equality
    @Test
    fun givenTwoIntegers_whenCheckValue_thenStructurallyEqual() {
        val a = Integer(10)
        val b = Integer(10)

        assertTrue(a == b)
    }
}