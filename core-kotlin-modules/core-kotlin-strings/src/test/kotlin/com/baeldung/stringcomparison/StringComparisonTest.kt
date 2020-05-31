package com.baeldung.stringcomparison

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringComparisonUnitTest {

    @Test
    fun `compare using equals operator`() {
        val first = "kotlin"
        val second = "kotlin"
        val firstCapitalized = "KOTLIN"
        assertTrue { first == second }
        assertFalse { first == firstCapitalized }
    }

    @Test
    fun `compare using referential equals operator`() {
        val first = "kotlin"
        val second = "kotlin"
        val third = String("kotlin".toCharArray())
        assertTrue { first === second }
        assertFalse { first === third }
    }

    @Test
    fun `compare using equals method`() {
        val first = "kotlin"
        val second = "kotlin"
        val firstCapitalized = "KOTLIN"
        assertTrue { first.equals(second) }
        assertFalse { first.equals(firstCapitalized) }
        assertTrue { first.equals(firstCapitalized, true) }
    }

    @Test
    fun `compare using compare method`() {
        val first = "kotlin"
        val second = "kotlin"
        val firstCapitalized = "KOTLIN"
        assertTrue { first.compareTo(second) == 0 }
        assertTrue { first.compareTo(firstCapitalized) == 32 }
        assertTrue { firstCapitalized.compareTo(first) == -32 }
        assertTrue { first.compareTo(firstCapitalized, true) == 0 }
    }
}