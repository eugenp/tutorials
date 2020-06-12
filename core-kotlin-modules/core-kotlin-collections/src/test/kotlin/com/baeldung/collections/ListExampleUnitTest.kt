package com.baeldung.collections

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import kotlin.test.assertFalse
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
    fun whenIterateUsingForEachLoop_thenSuccess() {
        assertEquals(7, classUnderTest.iterateUsingForEachLoop()[0])
    }

    @Test
    fun whenIterateUsingForLoop_thenSuccess() {
        assertEquals(5, classUnderTest.iterateUsingForLoop()[1])
    }

    @Test
    fun whenIterateUsingForLoopRange_thenSuccess() {
        assertEquals(6, classUnderTest.iterateUsingForLoopRange()[3])
    }

    @Test
    fun whenIterateUsingForEachIndexedLoop_thenSuccess() {
        assertEquals(9, classUnderTest.iterateUsingForEachIndexedLoop()[4])
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

    @Test
    fun whenRetrieveSubList_thenSuccess() {
        assertEquals(3, classUnderTest.retrieveSubList().size)
    }

    @Test
    fun whenRetrieveListSliceUsingIndices_thenSuccess() {
        assertEquals(4, classUnderTest.retrieveListSliceUsingIndices().size)
    }

    @Test
    fun whenRetrieveListSliceUsingIndicesList_thenSuccess() {
        assertEquals(2, classUnderTest.retrieveListSliceUsingIndicesList().size)
    }

    @Test
    fun whenCountList_thenSuccess() {
        assertEquals(5, classUnderTest.countList())
    }

    @Test
    fun whenCountListUsingPredicate_thenSuccess() {
        assertEquals(3, classUnderTest.countListUsingPredicate())
    }

    @Test
    fun whenCountListUsingProperty_thenSuccess() {
        assertEquals(5, classUnderTest.countListUsingProperty())
    }

    @Test
    fun whenAddToList_thenSuccess() {
        assertEquals(11, classUnderTest.addToList().count())
    }

    @Test
    fun whenRemoveFromList_thenSuccess() {
        val list = classUnderTest.removeFromList()
        assertEquals(3, list.size)
        assertEquals("Sao Paulo", list[1])
    }

    @Test
    fun whenReplaceFromList_thenSuccess() {
        val list = classUnderTest.replaceFromList()
        assertEquals(5, list.size)
        assertEquals("Barcelona", list[1])
    }

    @Test
    fun whenSortMutableList_thenSuccess() {
        assertEquals("Sydney", classUnderTest.sortMutableList()[0])
    }

    @Test
    fun whenSortList_thenSuccess() {
        assertEquals("India", classUnderTest.sortList()[1])
    }

    @Test
    fun whenCheckOneElementInList_thenSuccess() {
        assertTrue(classUnderTest.checkOneElementInList())
    }

    @Test
    fun whenCheckOneElementInListUsingOperator_thenSuccess() {
        assertFalse(classUnderTest.checkOneElementInListUsingOperator())
    }

    @Test
    fun whenCheckElementsInList_thenSuccess() {
        assertTrue(classUnderTest.checkElementsInList())
    }
}