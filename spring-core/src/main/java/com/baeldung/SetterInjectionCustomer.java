package com.baeldung.DIEval.DIEvaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjectionCustomer {
	
	private Address custAddress;

	@Autowired
	public void setAddress(Address address) {
		this.custAddress = address;
	}
	
	public String getCustCountry(String custID) {
		
		return custAddress.fetchCountryName(custID);
	}

}
