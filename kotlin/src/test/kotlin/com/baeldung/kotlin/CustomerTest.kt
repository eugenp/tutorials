package com.baeldung.kotlin

import com.baeldung.java.ArrayExample
import com.baeldung.java.Customer
import org.junit.Test
import kotlin.test.assertEquals

class CustomerTest {

    @Test
    fun givenCustomer_whenNameAndLastNameAreAssigned_thenComplete() {
        val customer = Customer()

        val list = ArrayList<String>()
        val item = list[0]

        // Setter method is being called
        customer.firstName = "Frodo"
        customer.lastName = "Baggins"

        // Getter method is being called
        assertEquals(customer.firstName, "Frodo")
        assertEquals(customer.lastName, "Baggins")
    }

}

