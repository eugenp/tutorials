package com.baeldung.kotlin.junit5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SimpleTest5 {

    @Test
    fun `isEmpty should return true for empty lists`() {
        val list = listOf<String>()
        Assertions.assertTrue(list::isEmpty)
    }

    @Test
    @Disabled
    fun `JUnit should complain and report failed assertions`() {
        Assertions.assertEquals(3, 4) {
            "Three does not equal four"
        }
    }
}
