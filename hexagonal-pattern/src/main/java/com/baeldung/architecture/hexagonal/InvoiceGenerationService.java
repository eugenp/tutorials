package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.beans.InvoiceGenerationRequest;
import com.baeldung.architecture.hexagonal.beans.InvoiceGenerationResponse;
import com.baeldung.architecture.hexagonal.beans.InvoiceTypes;

public class InvoiceGenerationService {

	public InvoiceGenerationResponse generateInvoice(InvoiceGenerationRequest invoiceGenRequest) {
		
		InvoiceGenerationResponse generatedResponse = new InvoiceGenerationResponse();
		generatedResponse.setInvoiceType(invoiceGenRequest.getInvoiceType());
		if(invoiceGenRequest.getInvoiceType().equals(InvoiceTypes.PAYMENT.name())) {
			generatedResponse.setGeneratedContent(getPaymentInvoice(invoiceGenRequest));
		}else if(invoiceGenRequest.getInvoiceType().equals(InvoiceTypes.REFUND.name())) {
			generatedResponse.setGeneratedContent(getRefundInvoice(invoiceGenRequest));
		}
		
		return generatedResponse;
	}

	private String getPaymentInvoice(InvoiceGenerationRequest invoiceGenRequest) {
		StringBuffer invoiceBody = new StringBuffer();
		invoiceBody.append("<html>");
		invoiceBody.append("<h1>---- Invoice Payment Receipt -----</h1>");
		invoiceBody.append("<pre>");
		invoiceBody.append("\nCompany Name : Sample Company");
		invoiceBody.append("\nCompany Address : #12-34, Newyork, United States, 12345;");
		invoiceBody.append("\n");
		invoiceBody.append("\n Customer Name : "+invoiceGenRequest.getCustomerName());
		invoiceBody.append("\n Invoice Number : "+invoiceGenRequest.getInvoiceNumber());
		invoiceBody.append("\n Date : "+invoiceGenRequest.getInvoiceDate());
		invoiceBody.append("\n Invoice Amount : "+invoiceGenRequest.getInvoiceDate());
		invoiceBody.append("</pre>");
		invoiceBody.append("</html>");
		return invoiceBody.toString();
	}
	
	private String getRefundInvoice(InvoiceGenerationRequest invoiceGenRequest) {
		StringBuffer invoiceBody = new StringBuffer();
		invoiceBody.append("<html>");
		invoiceBody.append("<h1>---- Refund Receipt -----</h1>");
		invoiceBody.append("<pre>");
		invoiceBody.append("\nCompany Name : Sample Company");
		invoiceBody.append("\nCompany Address : #12-34, Newyork, United States, 12345;");
		invoiceBody.append("\n");
		invoiceBody.append("\n Customer Name : "+invoiceGenRequest.getCustomerName());
		invoiceBody.append("\n Invoice Number : "+invoiceGenRequest.getInvoiceNumber());
		invoiceBody.append("\n Date : "+invoiceGenRequest.getInvoiceDate());
		invoiceBody.append("\n Refund Amount : "+invoiceGenRequest.getInvoiceNumber());
		invoiceBody.append("</pre>");
		invoiceBody.append("</html>");
		return invoiceBody.toString();
	}
	
	

}
