package com.baeldung.shallowndeepcopy;

public class CustomerShallowCopy implements Cloneable {
	public int customerId;
	public AddressShallowCopy addressShallowCopy;
	public String customerName;
	public String customerType;

	public CustomerShallowCopy(int customerId, AddressShallowCopy addressShallowCopy, String customerName,
			String customerType) {
		super();
		this.customerId = customerId;
		this.addressShallowCopy = addressShallowCopy;
		this.customerName = customerName;
		this.customerType = customerType;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
