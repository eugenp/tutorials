package com.baeldung.string.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomerPrimitiveToStringTest {

    public static final String CUSTOMER_PRIMITIVE_TO_STRING	= "Customer [balance=110, getFirstName()=Rajesh, getLastName()=Bhojwani]";

    @Test
    public void givingPrimitive_whenToString_thenCustomerDetails() {
        CustomerPrimitiveToString customer = new CustomerPrimitiveToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        customer.setBalance(110);
        
        assertEquals(CUSTOMER_PRIMITIVE_TO_STRING,customer.toString());
    }
}

