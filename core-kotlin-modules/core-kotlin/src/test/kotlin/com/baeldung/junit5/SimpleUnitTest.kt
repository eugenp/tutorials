package com.baeldung.junit5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SimpleUnitTest {

    @Test
    fun `isEmpty should return true for empty lists`() {
        val list = listOf<String>()
        Assertions.assertTrue(list::isEmpty)
    }

    @Test
    @Disabled
    fun `3 is equal to 4`() {
        Assertions.assertEquals(3, 4) {
            "Three does not equal four"
        }
    }
}
