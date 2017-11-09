package com.baeldung.DIEval.DIEvaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionCustomer {
	
	private Address custAddress;

	@Autowired
	public ConstructorInjectionCustomer(Address address) {
		this.custAddress = address;
	}
	
	public String fetchAddress(String custID) {
		return custAddress.fetchCountryName(custID);
	}

}

