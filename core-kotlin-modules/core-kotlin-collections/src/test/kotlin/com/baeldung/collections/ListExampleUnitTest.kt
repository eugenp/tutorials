package com.baeldung.collections

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import kotlin.test.assertTrue

class ListExampleUnitTest {

    private val classUnderTest: ListExample = ListExample()

    @Test
    fun whenListIsCreated_thenContainsElements() {
        assertTrue(classUnderTest.createList().contains("two"))
        assertTrue(classUnderTest.createMutableList().contains("Berlin"))
    }

}