package com.baeldung.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Customer {

	@Value("Thomas")
	private String customerName;

	@Value("Cust123")
	private String customerId;

	private Product product;

	private Address address;

	@Autowired
	public Customer(Address address) {
		super();
		this.address = address;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Product getProduct() {
		return product;
	}

	@Autowired
	public void setProduct(Product product) {
		this.product = product;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Customer [customerName=" + customerName + ", customerId=" + customerId + ", product=" + product
				+ ", address=" + address + "]";
	}

}
