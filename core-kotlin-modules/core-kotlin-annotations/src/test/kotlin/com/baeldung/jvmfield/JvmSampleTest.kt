package com.baeldung.jvmfield

import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class JvmSampleTest {

    var sample = ""

    @Before
    fun setUp() {
        sample = JvmSample("Hello!").sampleText
    }

    @Test
    fun givenField_whenCheckValue_thenMatchesValue() {
        assertTrue(sample == "Hello!")
    }

    @Test
    fun givenStaticVariable_whenCheckValue_thenMatchesValue() {
        // Sample when is treated as a static variable
        assertTrue(CompanionSample.MAX_LIMIT == 20)
    }
}