package org.baeldung.pojo.complex;

import java.util.List;

import org.baeldung.pojo.simple.Customer;

public class CustomerAddressDetails extends Customer {

	private List<AddressDetails> addressDetails;

	public List<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetails> addressDetails) {
		this.addressDetails = addressDetails;
	}
}
