package com.baeldung.equalityoperators

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EqualityTest {

    // Checks referential equality
    @Test
    fun givenTwoIntegers_whenCheckReference_thenEqualReference() {
        val a = Integer(10)
        val b = Integer(10)

        assertFalse(a === b)
    }

    // Checks structural equality
    @Test
    fun givenTwoIntegers_whenCheckValue_thenStructurallyEqual() {
        val a = Integer(10)
        val b = Integer(10)

        assertTrue(a == b)
    }

    @Test
    fun givenUser_whenCheckReference_thenEqualReference() {
        val user = User("John", 30, listOf("Hiking, Chess"))
        val user2 = User("Sarah", 28, listOf("Shopping, Gymnastics"))

        assertFalse(user === user2)
    }

    @Test
    fun givenUser_whenCheckValue_thenStructurallyEqual() {
        val user = User("John", 30, listOf("Hiking, Chess"))
        val user2 = User("John", 30, listOf("Hiking, Chess"))

        assertTrue(user == user2)
    }

    @Test
    fun givenArray_whenCheckReference_thenEqualReference() {
        val hobbies = arrayOf("Riding motorcycle, Video games")
        val hobbies2 = arrayOf("Riding motorcycle, Video games")

        assertFalse(hobbies === hobbies2)
    }

    @Test
    fun givenArray_whenCheckContent_thenStructurallyEqual() {
        val hobbies = arrayOf("Hiking, Chess")
        val hobbies2 = arrayOf("Hiking, Chess")

        assertTrue(hobbies contentEquals hobbies2)
    }
}