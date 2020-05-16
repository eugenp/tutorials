package com.baeldung.kotlin

import org.junit.Assert
import org.junit.Test

class ExtensionMethods {
    @Test
    fun simpleExtensionMethod() {
        Assert.assertEquals("Nothing", "Nothing".escapeForXml())
        Assert.assertEquals("&lt;Tag&gt;", "<Tag>".escapeForXml())
        Assert.assertEquals("a&amp;b", "a&b".escapeForXml())
    }

    @Test
    fun genericExtensionMethod() {
        fun <T> T.concatAsString(b: T) : String {
            return this.toString() + b.toString()
        }

        Assert.assertEquals("12", "1".concatAsString("2"))
        Assert.assertEquals("12", 1.concatAsString(2))
        // This doesn't compile
        // Assert.assertEquals("12", 1.concatAsString(2.0))
    }

    @Test
    fun infixExtensionMethod() {
        infix fun Number.toPowerOf(exponent: Number): Double {
            return Math.pow(this.toDouble(), exponent.toDouble())
        }

        Assert.assertEquals(9.0, 3 toPowerOf 2, 0.1)
        Assert.assertEquals(3.0, 9 toPowerOf 0.5, 0.1)
    }

    @Test
    fun operatorExtensionMethod() {
        operator fun List<Int>.times(by: Int): List<Int> {
            return this.map { it * by }
        }

        Assert.assertEquals(listOf(2, 4, 6), listOf(1, 2, 3) * 2)
    }
}
