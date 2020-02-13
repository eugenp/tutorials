package com.baeldung.hexagonal.infra;

import com.baeldung.hexagonal.domain.Quotation;
import com.baeldung.hexagonal.domain.QuotationSupplier;

public class FileQuotationSupplierAdapter implements QuotationSupplier {
	private String location;
	
	public String getLocation() {
		return this.location;
	}
	
	public FileQuotationSupplierAdapter(String location) {
		this.location = location;
	}

	@Override
	public Quotation suppy() {
		// Read from the file and return a random quotation
		Quotation quotation = new Quotation(1, "Generic Quotation");
		return quotation;
	}
	
}
