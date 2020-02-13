package com.baeldung.hexagonal.main;

import com.baeldung.hexagonal.app.ConsoleAdapter;
import com.baeldung.hexagonal.domain.QuotationPublisher;
import com.baeldung.hexagonal.domain.QuotationReader;
import com.baeldung.hexagonal.domain.QuotationSupplier;
import com.baeldung.hexagonal.infra.FileQuotationSupplierAdapter;

public class Main {
	private static String location = "quote.txt";
	
	public static void main(String[] args) {
		QuotationSupplier quotationSupplier = new FileQuotationSupplierAdapter(location);
		QuotationPublisher quotationPublisher = new QuotationReader(quotationSupplier);
		ConsoleAdapter consoleAdapter = new ConsoleAdapter(quotationPublisher);
		System.out.println(consoleAdapter.getQuotationPublisher().publish());
	}
}
