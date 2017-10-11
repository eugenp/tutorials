package com.baeldung.kotlin.junit5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SimpleTest5 {
    @Test
    fun testEmpty() {
        val list = listOf<String>()
        Assertions.assertTrue(list::isEmpty)
    }

    @Test
    @Disabled
    fun testMessage() {
        Assertions.assertEquals(3, 4) {
            "Three does not equal four"
        }
    }
}
