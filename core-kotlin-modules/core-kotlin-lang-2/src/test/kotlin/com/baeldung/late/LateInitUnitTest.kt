package com.baeldung.late

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LateInitUnitTest {

    private lateinit var answer: String

    @Test(expected = UninitializedPropertyAccessException::class)
    fun givenLateInit_WhenNotInitialized_ShouldThrowAnException() {
        answer.length
    }

    @Test
    fun givenLateInit_TheIsInitialized_ReturnsTheInitializationStatus() {
        assertFalse { this::answer.isInitialized }
        answer = "42"
        assertTrue { this::answer.isInitialized }
    }
}
