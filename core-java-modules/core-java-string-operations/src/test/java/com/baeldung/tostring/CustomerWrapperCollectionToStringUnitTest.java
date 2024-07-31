package com.baeldung.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CustomerWrapperCollectionToStringUnitTest {
    private static final String CUSTOMER_WRAPPER_COLLECTION_TO_STRING = "Customer [score=8, orders=[Book, Pen], fullname=Bhojwani, Rajesh, getFirstName()=Rajesh, getLastName()=Bhojwani]";

    @Test
    public void givenWrapperCollectionStrBuffer_whenToString_thenCustomerDetails() {
        CustomerWrapperCollectionToString customer = new CustomerWrapperCollectionToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        customer.setScore(8);

        List<String> orders = new ArrayList<String>();
        orders.add("Book");
        orders.add("Pen");
        customer.setOrders(orders);

        StringBuffer fullname = new StringBuffer();
        fullname.append(customer.getLastName() + ", " + customer.getFirstName());
        customer.setFullname(fullname);

        assertEquals(CUSTOMER_WRAPPER_COLLECTION_TO_STRING, customer.toString());
    }

}
