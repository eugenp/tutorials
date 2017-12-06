package com.baeldung.typesofinjection;

import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceServiceField {

	private DocumentService documentService;
	
	@Autowired
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public void storeInvoice(Invoice invoice) {
		this.documentService.saveDocument(invoice.getNumber(), invoice.getJson());
	}
	
}
