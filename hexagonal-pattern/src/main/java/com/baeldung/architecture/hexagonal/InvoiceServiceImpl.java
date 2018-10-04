package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.beans.InvoiceGenerationRequest;
import com.baeldung.architecture.hexagonal.beans.InvoiceRequest;

public class InvoiceServiceImpl implements InvoiceService<InvoiceRequest, InvoiceGenerationService> {

	@Override
	public Object generateInvoice(InvoiceRequest invoiceRequest, InvoiceGenerationService invoiceGenerationService) {
		try {
			InvoiceGenerationRequest invGenReq = createInvoiceGenerationRequest(invoiceRequest);
			return invoiceGenerationService.generateInvoice(invGenReq);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private InvoiceGenerationRequest createInvoiceGenerationRequest(InvoiceRequest invoiceRequest) {
		InvoiceGenerationRequest invGenReq = new InvoiceGenerationRequest();
		invGenReq.setInvoiceType(invoiceRequest.getInvoiceType());
		invGenReq.setCustomerName(invoiceRequest.getCustomer().getName());
		invGenReq.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
		invGenReq.setInvoiceDate(invoiceRequest.getInvoiceDate());
		invGenReq.setInvoiceAmount(invoiceRequest.getInvoiceProperties().get("Amount"));
		return invGenReq;
	}

}
