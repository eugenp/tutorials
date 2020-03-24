package com.baeldung.kotlin.delegates

import org.junit.Test
import kotlin.test.assertEquals

class DatabaseDelegatesTest {
    @Test
    fun testGetKnownFields() {
        val user = User(1)
        assertEquals("George", user.name)
        assertEquals(4, user.age)
    }

    @Test
    fun testSetKnownFields() {
        val user = User(2)
        user.age = 3
        assertEquals(3, user.age)
    }

    @Test(expected = NoRecordFoundException::class)
    fun testGetKnownField() {
        val user = User(3)
        user.name
    }
}
