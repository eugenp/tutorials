package com.baeldung.beaninjection.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beaninjection.Address;

@Component("EmployeeWithConstructorInjection")
public class Employee {

	private Address address;

	@Autowired
	public Employee(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [address=" + address + "]";
	}
	
}
