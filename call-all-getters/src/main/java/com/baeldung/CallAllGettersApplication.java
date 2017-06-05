package com.baeldung;

import java.util.List;

import com.baeldung.reflection.model.Customer;
import com.baeldung.reflection.util.Utils;

public class CallAllGettersApplication {

	public static void main(String[] args) throws Exception {
	    
	    Customer customer = new Customer(1, "Himanshu", null, null);
	    List<String> nullProps = Utils.getNullPropertiesList(customer);
	    System.out.println(nullProps);
	}
	
	
}
