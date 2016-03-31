package com.baeldung.pojo.complex;

import java.util.List;

public class CustomerPortfolioComplex {

	private List<CustomerAddressDetails> customerAddressDetailsList;

	public List<CustomerAddressDetails> getCustomerAddressDetailsList() {
		return customerAddressDetailsList;
	}

	public void setCustomerAddressDetailsList(List<CustomerAddressDetails> customerAddressDetailsList) {
		this.customerAddressDetailsList = customerAddressDetailsList;
	}

}
