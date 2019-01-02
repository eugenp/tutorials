package com.baeldung.string.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CustomerPrimitiveToStringTest {

	public static final String CUSTOMER_PRIMITIVE_TO_STRING	= "Customer [balance=110, getFirstName()=Rajesh, getLastName()=Bhojwani]";
    public static final String CUSTOMER_COMPLEX_TO_STRING =	"Customer [orders=Order [orderId=A1111, desc=Game, value=0, status=In-Shiping], getFirstName()=Rajesh, getLastName()=Bhojwani]";
    public static final String CUSTOMER_ARRAY_TO_STRING	= "Customer [orders=[Order [orderId=A1111, desc=Game, value=0, status=In-Shiping]], getFirstName()=Rajesh, getLastName()=Bhojwani]";
    public static final String CUSTOMER_WRAPPER_COLLECTION_TO_STRING = "Customer [score=8, orders=[Book, Pen], fullname=Bhojwani, Rajesh, getFirstName()=Rajesh, getLastName()=Bhojwani]";
    public static final String CUSTOMER_REFLECTION_TO_STRING = "com.baeldung.string.tostring.CustomerReflectionToString";
    
    @Test
    public void givingPrimitive_whenToString_thenCustomerDetails() {
        CustomerPrimitiveToString customer = new CustomerPrimitiveToString();
        customer.setFirstName("Rajesh");
        customer.setLastName("Bhojwani");
        customer.setBalance(110);
        
        assertEquals(CUSTOMER_PRIMITIVE_TO_STRING,customer.toString());
    }
    
	@Test
    public void givingComplex_whenToString_thenCustomerDetails() {
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
	
	@Test
    public void givingArray_whenToString_thenCustomerDetails() {
		CustomerArrayToString customer = new CustomerArrayToString();
		customer.setFirstName("Rajesh");
		customer.setLastName("Bhojwani");
		Order[] orders = new Order[1];  
	    orders[0] = new Order();
	    orders[0].setOrderId("A1111");
	    orders[0].setDesc("Game");
	    orders[0].setStatus("In-Shiping");
        customer.setOrders(orders);         
        
        assertEquals(CUSTOMER_ARRAY_TO_STRING,customer.toString());
    }

	@Test
	public void givingWrapperCollectionStrBuffer_whenToString_thenCustomerDetails() {
		CustomerWrapperCollectionToString customer = new CustomerWrapperCollectionToString();
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
		
		assertEquals(CUSTOMER_WRAPPER_COLLECTION_TO_STRING,customer.toString());
	}
	
	@Test
    public void givingWrapperCollectionStrBuffer_whenReflectionToString_thenCustomerDetails() {
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

