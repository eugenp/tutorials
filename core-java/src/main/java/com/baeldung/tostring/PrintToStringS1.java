package com.baeldung.tostring;

public class PrintToStringS1{
    public static void main(String ...args){
        CustomerS1 customer = new CustomerS1();
		customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
 
        System.out.println( customer);
        
    }
}