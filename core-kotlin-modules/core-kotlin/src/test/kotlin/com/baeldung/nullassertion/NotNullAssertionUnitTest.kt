package com.baeldung.nullassertion

import org.junit.Test
import kotlin.test.assertEquals

class NotNullAssertionUnitTest {

    @Test
    fun givenNullableValue_WhenNotNull_ShouldExtractTheValue() {
        val answer: String? = "42"

        assertEquals(42, answer!!.toInt())
    }

    @Test(expected = KotlinNullPointerException::class)
    fun givenNullableValue_WhenIsNull_ThenShouldThrow() {
        val noAnswer: String? = null
        noAnswer!!
    }
}
