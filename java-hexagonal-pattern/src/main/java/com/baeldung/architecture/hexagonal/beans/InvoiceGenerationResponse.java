package com.baeldung.architecture.hexagonal.beans;

public class InvoiceGenerationResponse {

	private String invoiceType;

	private String generatedContent;

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getGeneratedContent() {
		return generatedContent;
	}

	public void setGeneratedContent(String generatedContent) {
		this.generatedContent = generatedContent;
	}

}
