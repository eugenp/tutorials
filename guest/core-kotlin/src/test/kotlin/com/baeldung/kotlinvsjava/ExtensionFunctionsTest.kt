package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals

class ExtensionFunctionsTest {

    @Test
    fun givenAStringWithAnExtensionFunction_whenCallingThatFunction_thenItConcatenatesStrings() {
        val sampleString = "ABC"
        val concatenatedString = sampleString.appendString("DEF")

        assertEquals("ABCDEF", concatenatedString)
    }

    @Test
    fun givenAStringWithAnExtensionProperty_whenReadingProperty_thenItReturnsLengthOfString() {
        val sampleString = "ABC"

        assertEquals(3, sampleString.size)
    }

    fun String.appendString(str : String): String {
        return plus(str)
    }

    val String.size: Int
        get() = length

}