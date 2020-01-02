package com.baeldung.listtomap

import org.junit.Test
import kotlin.test.assertTrue

class ListToMapTest {

    val user1 = User("John", 18, listOf("Hiking, Swimming"))
    val user2 = User("Sara", 25, listOf("Chess, Board Games"))
    val user3 = User("Dave", 34, listOf("Games, Racing sports"))
    val user4 = User("John", 30, listOf("Reading, Poker"))

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

    @Test
    fun givenStringList_whenAssociate_thenResult() {
        val myList = listOf("a", "b", "c", "c", "b")
        val myMap = myList.associate{ it to it }

        assertTrue(myMap.get("a") == "a")
    }

    @Test
    fun givenStringList_whenAssociateTo_thenResult() {
        val myList = listOf("a", "b", "c", "c", "b")
        val myMap = mutableMapOf<String, String>()

        myList.associateTo(myMap) {it to it}

        assertTrue(myMap.get("a") == "a")
    }

    @Test
    fun givenStringList_whenAssociateByTo_thenResult() {
        val myList = listOf(user1, user2, user3, user4)
        val myMap = mutableMapOf<String, Int>()

        myList.associateByTo(myMap, {it.name}, {it.age})

        assertTrue(myMap.get("Dave") == 34)
    }

    @Test
    fun givenStringList_whenAssociateByToUser_thenResult() {
        val myList = listOf(user1, user2, user3, user4)
        val myMap = mutableMapOf<String, User>()

        myList.associateByTo(myMap) {it.name}

        assertTrue(myMap.get("Dave")!!.age == 34)
    }
}