package com.baeldung.utility;

import java.util.Calendar;

import com.baeldung.pojo.Customer;

public class SimpleDataGeneration {

	public static Customer generateData() {
		Customer customer = new Customer();
		Calendar cal = Calendar.getInstance();
		cal.set(1986 , 01 , 14);
		customer.setDob(cal.getTime());
		customer.setFirstName("Xstream");
		customer.setLastName("Java");
		
		return customer;
	}
}
