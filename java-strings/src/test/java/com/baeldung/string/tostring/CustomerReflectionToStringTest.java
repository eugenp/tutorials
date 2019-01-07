package com.baeldung.string.tostring;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CustomerReflectionToStringTest {
    private static final String CUSTOMER_REFLECTION_TO_STRING = "com.baeldung.string.tostring.CustomerReflectionToString";
    	 
    @Test
    public void givenWrapperCollectionStrBuffer_whenReflectionToString_thenCustomerDetails() {
	CustomerReflectionToString customer = new CustomerReflectionToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        customer.setScore(8);
        
        List<String> orders = new ArrayList<String>();
        orders.add("Book");
        orders.add("Pen");
        customer.setOrders(orders);
   
        StringBuffer fullname = new StringBuffer();
        fullname.append(customer.getLastName()+", "+ customer.getFirstName());
        customer.setFullname(fullname);
 
        assertTrue(customer.toString().contains(CUSTOMER_REFLECTION_TO_STRING));
    }

}
