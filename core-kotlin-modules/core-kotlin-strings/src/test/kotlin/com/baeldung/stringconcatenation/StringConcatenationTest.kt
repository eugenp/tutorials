package com.baeldung.stringconcatenation

import org.junit.Test
import kotlin.test.assertEquals

class StringConcatenationTest {

    @Test
    fun givenTwoStrings_concatenateWithTemplates_thenEquals() {
        val a = "Hello"
        val b = "Baeldung"
        val c = "$a $b"

        assertEquals("Hello Baeldung", c)
    }

    @Test
    fun givenTwoStrings_concatenateWithPlusOperator_thenEquals() {
        val a = "Hello"
        val b = "Baeldung"
        val c = a + " " + b

        assertEquals("Hello Baeldung", c)
    }

    @Test
    fun givenTwoStrings_concatenateWithStringBuilder_thenEquals() {
        val a = "Hello"
        val b = "Baeldung"

        val builder = StringBuilder()
        builder.append(a).append(" ").append(b)

        val c = builder.toString()

        assertEquals("Hello Baeldung", c)
    }

    @Test
    fun givenTwoStrings_concatenateWithPlusMethod_thenEquals() {
        val a = "Hello"
        val b = "Baeldung"
        val c = a.plus(" ").plus(b)

        assertEquals("Hello Baeldung", c)
    }

}
