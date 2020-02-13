package com.baeldung.hexagonal.app;

import com.baeldung.hexagonal.domain.QuotationPublisher;

public class ConsoleAdapter {
	private QuotationPublisher quotationPublisher;
	public ConsoleAdapter(QuotationPublisher quotationPublisher) {
		this.quotationPublisher = quotationPublisher;
	}
	
	public QuotationPublisher getQuotationPublisher() {
		return this.quotationPublisher;
	}
}
