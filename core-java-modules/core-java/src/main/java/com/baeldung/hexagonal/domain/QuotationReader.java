package com.baeldung.hexagonal.domain;

public class QuotationReader implements QuotationPublisher {
	private QuotationSupplier quotationSupplier;
	public QuotationReader(QuotationSupplier quotationSupplier) {
		this.quotationSupplier = quotationSupplier;
	}
	
	public QuotationSupplier getQuotationSupplier() {
		return this.quotationSupplier;
	}

	@Override
	public Quotation publish() {
		return quotationSupplier.suppy();
	}
}
