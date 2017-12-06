package com.baeldung.typesofinjection;

import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceServiceSetterMethod {

	@Autowired
	private DocumentService documentService;
	
	public void storeInvoice(Invoice invoice) {
		this.documentService.saveDocument(invoice.getNumber(), invoice.getJson());
	}
	
}
