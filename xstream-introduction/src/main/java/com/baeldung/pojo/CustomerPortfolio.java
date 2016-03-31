package com.baeldung.pojo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CustomerPortfolio")
public class CustomerPortfolio {

	private List<CustomerAddressDetails> customerAddressDetailsList;

	public List<CustomerAddressDetails> getCustomerAddressDetailsList() {
		return customerAddressDetailsList;
	}

	public void setCustomerAddressDetailsList(List<CustomerAddressDetails> customerAddressDetailsList) {
		this.customerAddressDetailsList = customerAddressDetailsList;
	}

}
