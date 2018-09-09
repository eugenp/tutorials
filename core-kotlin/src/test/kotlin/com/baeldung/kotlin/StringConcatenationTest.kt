package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertEquals

class SealedTest {

    @Test
    fun givenTwoStrings_concatenateWithTemplates_thenCorrect() {
        val a = "Hello"
        val b = "Baeldung"
        val c = "$a $b"

        assertEquals("Hello Baeldung", c)
    }

    @Test
    fun givenTwoStrings_concatenateWithPlusOperator_thenCorrect() {
        val a = "Hello"
        val b = "Baeldung"
        val c = a + " " + b

        assertEquals("Hello Baeldung", c)
    }

    @Test
    fun givenTwoStrings_concatenateWithStringBuilder_thenCorrect() {
        val a = "Hello"
        val b = "Baeldung"

        val builder = StringBuilder()
        builder.append(a).append(" ").append(b)

        val c = builder.toString()

        assertEquals("Hello Baeldung", c)
    }

    @Test
    fun givenTwoStrings_concatenateWithPlusMethod_thenCorrect() {
        val a = "Hello"
        val b = "Baeldung"
        val c = a.plus(" ").plus(b)

        assertEquals("Hello Baeldung", c)
    }

}
