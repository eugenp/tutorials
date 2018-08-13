package com.baeldung.kotlinvsjava

import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals

class ExceptionsTest {

    @Test
    fun givenATryExpression_whenReturning5InLastExpressionOfTryBlock_thenWeGet5() {
        val value: Int = try { 5 } catch (e: IOException) { 6 }

        assertEquals(5, value)
    }

    @Test
    fun givenATryExpression_whenReturning6InLastExpressionOfCatchBlock_thenWeGet6() {
        val value: Int = try { funThrowingException() } catch (e: IOException) { 6 }

        assertEquals(6, value)
    }

    @org.junit.Test(expected = IllegalArgumentException::class)
    fun givenANullString_whenUsingElvisOperator_thenExceptionIsThrown() {
        val sampleString: String? = null

        val length: Int = sampleString?.length ?: throw IllegalArgumentException("String must not be null")
    }

    private fun funThrowingException(): Nothing {
        throw IOException()
    }

}