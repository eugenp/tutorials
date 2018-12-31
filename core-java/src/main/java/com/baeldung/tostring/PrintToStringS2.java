package com.baeldung.tostring;

public class PrintToStringS2{
    public static void main(String ...args){
        CustomerS2 customer = new CustomerS2();
		customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
 
        Order orders    =    new Order();
        orders.setOrderId("A1111");
        orders.setDesc("Game");
        orders.setStatus("In-Shiping");
        customer.setOrders(orders);
     
        System.out.println( customer);
        
    }
}