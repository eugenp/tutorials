package com.baeldung.string.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomerComplexObjectToStringTest {
    private static final String CUSTOMER_COMPLEX_TO_STRING 
      = "Customer [orders=Order [orderId=A1111, desc=Game, value=0], getFirstName()=Rajesh, getLastName()=Bhojwani]";
	 
    @Test
    public void givenComplex_whenToString_thenCustomerDetails() {
        CustomerComplexObjectToString customer = new CustomerComplexObjectToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        Order orders    =    new Order();
        orders.setOrderId("A1111");
        orders.setDesc("Game");
        orders.setStatus("In-Shiping");
        customer.setOrders(orders);
             
        assertEquals(CUSTOMER_COMPLEX_TO_STRING,customer.toString());
    }

}
