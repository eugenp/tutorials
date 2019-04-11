package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.math.absoluteValue
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IsOperatorTest {

    @Test
    fun givenSampleValue_whenUsingIsOperatorInIfStatement_thenItCastsAutomaticallyToString() {
        val value: Any = "string"

        if(value is String) {
            assertEquals(6, value.length)
        }
    }

    @Test
    fun givenSampleValue_whenUsingIsOperatorWithAndOperator_thenItCastsAutomaticallyToString() {
        val value: Any = "string"

        assertTrue(value is String && value.length == 6)
    }

    @Test
    fun givenSampleValue_whenUsingWithWhenOperator_thenItCastsAutomaticallyToString() {
        val value: Any = "string"

        when(value) {
            is String -> assertEquals(6, value.length)
            is Int -> assertEquals(6, value.absoluteValue)
        }
    }

}