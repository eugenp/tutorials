package com.baeldung.lambda

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ListsTest {

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

}