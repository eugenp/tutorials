package com.baeldung.lists

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListsUnitTest {

    var batmans: List<String> = listOf("Christian Bale", "Michael Keaton", "Ben Affleck", "George Clooney")

    @Test
    fun whenFindASpecificItem_thenItemIsReturned() {
        //Returns the first element matching the given predicate, or null if no such element was found.
        val theFirstBatman = batmans.find { actor -> "Michael Keaton".equals(actor) }
        assertEquals(theFirstBatman, "Michael Keaton")
    }

    @Test
    fun whenFilterWithPredicate_thenMatchingItemsAreReturned() {
        //Returns a list containing only elements matching the given predicate.
        val theCoolestBatmans = batmans.filter { actor -> actor.contains("a") }
        assertTrue(theCoolestBatmans.contains("Christian Bale") && theCoolestBatmans.contains("Michael Keaton"))
    }

    @Test
    fun whenFilterNotWithPredicate_thenMatchingItemsAreReturned() {
        //Returns a list containing only elements not matching the given predicate.
        val theMehBatmans = batmans.filterNot { actor -> actor.contains("a") }
        assertFalse(theMehBatmans.contains("Christian Bale") && theMehBatmans.contains("Michael Keaton"))
        assertTrue(theMehBatmans.contains("Ben Affleck") && theMehBatmans.contains("George Clooney"))
    }

}