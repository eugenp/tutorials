package com.baeldung.beaninjection.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baeldung.beaninjection.Address;

@Component("EmployeeWithFieldInjection")
public class Employee {

	@Autowired
	private Address address;
	
	@Override
	public String toString() {
		return "Employee [address=" + address + "]";
	}
}
