package com.baeldung.tostring;

public class PrintToStringS3{
    public static void main(String ...args){
        CustomerS3 customer = new CustomerS3();
		customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
 
        Order[] orders = new Order[1];  
	    orders[0] = new Order();
	    orders[0].setOrderId("A1111");
	    orders[0].setDesc("Game");
	    orders[0].setStatus("In-Shiping");
        customer.setOrders(orders);
        
        System.out.println( customer);
        
    }
}