package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;

import com.baeldung.hexagonal.persistance.InvoicePersistancePort;

public interface InvoiceServicePort {
    void storeNewInvoice(BigDecimal amount, InvoicePersistancePort persistancePort);
}
