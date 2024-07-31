package com.baeldung.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomerArrayToStringUnitTest {
    private static final String CUSTOMER_ARRAY_TO_STRING = "Customer [orders=[Order [orderId=A1111, desc=Game, value=0]], getFirstName()=Rajesh, getLastName()=Bhojwani]";

    @Test
    public void givenArray_whenToString_thenCustomerDetails() {
        CustomerArrayToString customer = new CustomerArrayToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        Order[] orders = new Order[1];
        orders[0] = new Order();
        orders[0].setOrderId("A1111");
        orders[0].setDesc("Game");
        orders[0].setStatus("In-Shiping");
        customer.setOrders(orders);

        assertEquals(CUSTOMER_ARRAY_TO_STRING, customer.toString());
    }

}
