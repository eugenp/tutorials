package com.baeldung.kotlin.junit5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SimpleTest5 {
    @Test
    fun whenEmptyList_thenListIsEmpty() {
        val list = listOf<String>()
        Assertions.assertTrue(list::isEmpty)
    }

    @Test
    @Disabled
    fun when3equals4_thenTestFails() {
        Assertions.assertEquals(3, 4) {
            "Three does not equal four"
        }
    }
}
