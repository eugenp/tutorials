package org.baeldung.dependency.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class Employee {	
	private Address address;
	
	public Employee(){
		
	}
	
	public Employee(Address address){
		this.address = address;
	}
	
	public Address getAddress() {
		return address;
	}

	@Autowired	
	public void setAddress(Address address) {
		this.address = address;
	}

}
