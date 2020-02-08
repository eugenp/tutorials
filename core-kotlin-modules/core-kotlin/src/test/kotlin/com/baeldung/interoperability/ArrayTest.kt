package com.baeldung.interoperability

import org.junit.Test
import kotlin.test.assertEquals

class ArrayTest {

    @Test
    fun givenArray_whenValidateArrayType_thenComplete () {
        val ex = ArrayExample()
        val numArray = intArrayOf(1, 2, 3)

        assertEquals(ex.sumValues(numArray), 6)
    }

    @Test
    fun givenCustomer_whenGetSuperType_thenComplete() {
        val instance = Customer::class
        val supertypes = instance.supertypes

        assertEquals(supertypes[0].toString(), "kotlin.Any")
    }

    @Test
    fun givenCustomer_whenGetConstructor_thenComplete() {
        val instance = Customer::class.java
        val constructors = instance.constructors

        assertEquals(constructors.size, 1)
        assertEquals(constructors[0].name, "com.baeldung.interoperability.Customer")
    }

    fun makeReadFile() {
        val ax = ArrayExample()
        ax.writeList()
    }
}