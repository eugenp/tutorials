package com.baeldung.collections

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListExampleUnitTest {

    private val classUnderTest: ListExample = ListExample()

    @Test
    fun whenListIsCreated_thenContainsElements() {
        assertTrue(classUnderTest.createList().contains("India"))
        assertTrue(classUnderTest.createMutableList().contains("Seoul"))
    }

    @Test
    fun whenRetrieveElementsInList_thenSuccess() {
        assertEquals("Japan", classUnderTest.retrieveElementsInList())
    }

    @Test
    fun whenRetrieveElementsUsingGet_thenSuccess() {
        assertEquals("Brazil", classUnderTest.retrieveElementsUsingGet())
    }

    @Test
    fun whenRetrieveElementsFirstAndLast_thenSuccess() {
        assertEquals("Australia", classUnderTest.retrieveElementsFirstAndLast())
    }
}