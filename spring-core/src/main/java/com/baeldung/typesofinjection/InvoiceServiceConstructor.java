package com.baeldung.typesofinjection;

import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceServiceConstructor {

	private final DocumentService documentService;
	
	@Autowired
	public InvoiceServiceConstructor(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public void storeInvoice(Invoice invoice) {
		this.documentService.saveDocument(invoice.getNumber(), invoice.getJson());
	}
	
}
