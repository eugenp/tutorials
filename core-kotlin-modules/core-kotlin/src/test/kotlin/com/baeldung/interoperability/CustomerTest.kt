package com.baeldung.interoperability

import org.junit.Test
import kotlin.test.assertEquals

class CustomerTest {

    @Test
    fun givenCustomer_whenNameAndLastNameAreAssigned_thenComplete() {
        val customer = Customer()

        // Setter method is being called
        customer.firstName = "Frodo"
        customer.lastName = "Baggins"

        // Getter method is being called
        assertEquals(customer.firstName, "Frodo")
        assertEquals(customer.lastName, "Baggins")
    }

}

