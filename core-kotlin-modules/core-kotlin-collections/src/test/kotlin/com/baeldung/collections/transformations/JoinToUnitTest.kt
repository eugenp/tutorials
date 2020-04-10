package com.baeldung.collections.transformations

import org.junit.Assert.assertEquals
import org.junit.Test

class JoinToUnitTest {
    @Test
    fun testJoinToString() {
        val inputs = listOf("Jan", "Feb", "Mar", "Apr", "May")

        val simpleString = inputs.joinToString()
        assertEquals("Jan, Feb, Mar, Apr, May", simpleString)

        val detailedString = inputs.joinToString(separator = ",", prefix="Months: ", postfix=".")
        assertEquals("Months: Jan,Feb,Mar,Apr,May.", detailedString)
    }

    @Test
    fun testJoinToStringLimits() {
        val inputs = listOf("Jan", "Feb", "Mar", "Apr", "May")

        val simpleString = inputs.joinToString(limit = 3)
        assertEquals("Jan, Feb, Mar, ...", simpleString)
    }

    @Test
    fun testJoinToStringTransform() {
        val inputs = listOf("Jan", "Feb", "Mar", "Apr", "May")

        val simpleString = inputs.joinToString(transform = String::toUpperCase)
        assertEquals("JAN, FEB, MAR, APR, MAY", simpleString)
    }

    @Test
    fun testJoinTo() {
        val inputs = listOf("Jan", "Feb", "Mar", "Apr", "May")

        val output = StringBuilder()
        output.append("My ")
                .append(inputs.size)
                .append(" elements: ")
        inputs.joinTo(output)

        assertEquals("My 5 elements: Jan, Feb, Mar, Apr, May", output.toString())
    }
}