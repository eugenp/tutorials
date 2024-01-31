package com.baeldung.shallowndeepcopy;

public class AddressShallowCopy {
	public String addressLine1;
	public int pincode;
	public String state;
	public String city;

	public AddressShallowCopy(String addressLine1, int pincode, String state, String city) {
		super();
		this.addressLine1 = addressLine1;
		this.pincode = pincode;
		this.state = state;
		this.city = city;
	}
}
