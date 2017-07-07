package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertTrue

class ListToMapTest {

    val user1 = User("John", 18, listOf("Hiking, Swimming"))
    val user2 = User("Sara", 25, listOf("Chess, Board Games"))
    val user3 = User("Dave", 34, listOf("Games, Racing sports"))

    @Test
    fun givenList_whenConvertToMap_thenResult() {
        val myList = listOf(user1, user2, user3)
        val myMap = myList.map { it.name to it.age }.toMap()

        assertTrue(myMap.get("John") == 18)
    }

    @Test
    fun givenList_whenAssociatedBy_thenResult() {
        val myList = listOf(user1, user2, user3)
        val myMap = myList.associateBy({ it.name }, { it.hobbies })

        assertTrue(myMap.get("John")!!.contains("Hiking, Swimming"))
    }

    @Test
    fun givenStringList_whenConvertToMap_thenResult() {
        val myList = listOf("a", "b", "c")
        val myMap = myList.map { it to it }.toMap()

        assertTrue(myMap.get("a") == "a")
    }
}