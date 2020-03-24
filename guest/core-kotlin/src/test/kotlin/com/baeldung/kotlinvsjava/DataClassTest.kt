package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class DataClassTest {

    @Test
    fun givenASampleDataClass_whenCallingToStringMethod_thenItReturnsAllProperties() {
        val student = Student(1, "John", "Smith")

        assertEquals(1, student.id)
        assertEquals("John", student.name)
        assertEquals("Smith", student.lastName)
        assertEquals("Student(id=1, name=John, lastName=Smith)", student.toString())
    }

    @Test
    fun givenASampleDataClass_whenCreatingACopyWithGeneratedFunction_thenItReturnsACopyWithRequestedChanges() {
        val student = Student(1, "John", "Smith")
        val student2 = student.copy(id = 2, name = "Anne")

        assertEquals(2, student2.id)
        assertEquals("Anne", student2.name)
        assertEquals("Smith", student2.lastName)
        assertEquals("Student(id=2, name=Anne, lastName=Smith)", student2.toString())
        assertFalse(student.equals(student2))
    }

}

data class Student(val id: Int, val name: String, val lastName: String)