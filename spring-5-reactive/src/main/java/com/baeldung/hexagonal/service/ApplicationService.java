package com.baeldung.hexagonal.service;

import java.math.BigDecimal;

import com.baeldung.hexagonal.domain.InvoiceServicePort;
import com.baeldung.hexagonal.persistance.InvoicePersistancePort;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationService {

    @Autowired
    InvoicePersistancePort invoicePersistancePort;

    @Autowired
    InvoiceServicePort invoiceServicePort;

    public void createInvoice() {
        invoiceServicePort.storeNewInvoice(new BigDecimal(150.0), invoicePersistancePort);
    }
}
