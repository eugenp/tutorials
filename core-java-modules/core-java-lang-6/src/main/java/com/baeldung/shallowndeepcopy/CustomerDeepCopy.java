package com.baeldung.shallowndeepcopy;

public class CustomerDeepCopy implements Cloneable {
	public int customerId;
	public AddressDeepCopy addressDeepCopy;
	public String customerName;
	public String customerType;

	public CustomerDeepCopy(int customerId, AddressDeepCopy addressDeepCopy, String customerName, String customerType) {
		super();
		this.customerId = customerId;
		this.addressDeepCopy = addressDeepCopy;
		this.customerName = customerName;
		this.customerType = customerType;
	}

	protected Object clone() throws CloneNotSupportedException {
		CustomerDeepCopy cust = (CustomerDeepCopy) super.clone();
		cust.addressDeepCopy = (AddressDeepCopy) addressDeepCopy.clone();

		return cust;
	}
}
