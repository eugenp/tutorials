package com.baeldung.ternary

import org.junit.Test
import kotlin.test.assertEquals

class TernaryOperatorTest {

    @Test
    fun `using If`() {
        val a = true
        val result = if (a) "yes" else "no"
        assertEquals("yes", result)
    }

    @Test
    fun `using When`() {
        val a = true
        val result = when(a) {
            true -> "yes"
            false -> "no"
        }
        assertEquals("yes", result)
    }
}