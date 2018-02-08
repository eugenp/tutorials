package com.baeldung.beaninjection;

public class Address {

	String street;

	long pincode;

	@Override
	public String toString() {
		return "Address [street=" + street + ", pincode=" + pincode + "]";
	}
	
}
