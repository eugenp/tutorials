package com.baeldung.architecture.hexagonal;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.architecture.hexagonal.beans.CustomerDetails;
import com.baeldung.architecture.hexagonal.beans.InvoiceGenerationResponse;
import com.baeldung.architecture.hexagonal.beans.InvoiceRequest;
import com.baeldung.architecture.hexagonal.beans.InvoiceTypes;

public class Main {

	public static void main(String[] args) {

		InvoiceGenerationService invoiceGenrationService = new InvoiceGenerationService();

		InvoiceService<InvoiceRequest, InvoiceGenerationService> invoiceService = new InvoiceServiceImpl();

		CustomerDetails customer = new CustomerDetails();
		customer.setEmail("testuser@baeldungtest.com");
		customer.setName("Test User");

		InvoiceRequest invoiceRequest = new InvoiceRequest();
		invoiceRequest.setCustomer(customer);
		invoiceRequest.setInvoiceDate("2018-Sep-17");
		invoiceRequest.setInvoiceNumber("INV12345");
		invoiceRequest.setInvoiceType(InvoiceTypes.PAYMENT.name());
		Map<String, String> invoiceProperties = new HashMap<>();
		invoiceProperties.put("Amount", "$100");
		invoiceRequest.setInvoiceProperties(invoiceProperties);

		InvoiceGenerationResponse response = (InvoiceGenerationResponse) invoiceService.generateInvoice(invoiceRequest,
				invoiceGenrationService);
		System.out.println("Generated Invoice : \n" + response.getGeneratedContent());
	}

}
