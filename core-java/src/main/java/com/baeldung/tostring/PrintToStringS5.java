package com.baeldung.tostring;

import java.util.ArrayList;
import java.util.List;

public class PrintToStringS5{
    public static void main(String[] args) {

        CustomerS5 customer = new CustomerS5();
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