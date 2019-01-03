package com.baeldung.string.tostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class test {
 

 
       
  @Test
  public void givenComplex_whenToString_thenCustomerDetails() {
      final String CUSTOMER_COMPLEX_TO_STRING 
        = "Customer [orders=Order [orderId=A1111, desc=Game, value=0, status=In-Shiping], getFirstName()=Rajesh, getLastName()=Bhojwani]";
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
