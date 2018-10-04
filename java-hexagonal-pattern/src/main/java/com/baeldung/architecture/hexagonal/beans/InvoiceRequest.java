package com.baeldung.architecture.hexagonal.beans;

import java.util.Map;

public class InvoiceRequest {

	private String invoiceType;

	private String invoiceNumber;

	private String invoiceDate;

	private CustomerDetails customer;

	private Map<String, String> invoiceProperties;

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Map<String, String> getInvoiceProperties() {
		return invoiceProperties;
	}

	public void setInvoiceProperties(Map<String, String> invoiceProperties) {
		this.invoiceProperties = invoiceProperties;
	}

	public CustomerDetails getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDetails customer) {
		this.customer = customer;
	}

}
