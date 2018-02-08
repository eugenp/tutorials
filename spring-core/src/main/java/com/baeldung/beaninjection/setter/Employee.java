package com.baeldung.beaninjection.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baeldung.beaninjection.Address;

@Component("EmployeeWithSetterInjection")
public class Employee {

	private Address address;

	@Autowired
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Employee [address=" + address + "]";
	}
}
