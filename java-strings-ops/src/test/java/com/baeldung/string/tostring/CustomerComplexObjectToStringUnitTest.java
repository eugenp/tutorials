package com.baeldung.string.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomerComplexObjectToStringUnitTest {
    private static final String CUSTOMER_COMPLEX_TO_STRING 
      = "Customer [order=Order [orderId=A1111, desc=Game, value=0], getFirstName()=Rajesh, getLastName()=Bhojwani]";
	 
    @Test
    public void givenComplex_whenToString_thenCustomerDetails() {
        CustomerComplexObjectToString customer = new CustomerComplexObjectToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        Order order    =    new Order();
        order.setOrderId("A1111");
        order.setDesc("Game");
        order.setStatus("In-Shiping");
        customer.setOrder(order);
             
        assertEquals(CUSTOMER_COMPLEX_TO_STRING, customer.toString());
    }

}
