package com.baeldung.architecture.hexagonal;

public interface InvoiceService<T, TCOMMAND> {
	
	public Object generateInvoice(T request, TCOMMAND commandObject);

}
