package com.baeldung.tostring;

import java.util.ArrayList;
import java.util.List;

public class PrintToStringS4{
    public static void main(String[] args) {

        CustomerS4 customer = new CustomerS4();
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
        
        System.out.println( customer);
        
    }
}