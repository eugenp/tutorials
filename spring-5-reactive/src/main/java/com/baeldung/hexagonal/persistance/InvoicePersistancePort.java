package com.baeldung.hexagonal.persistance;

import com.baeldung.hexagonal.domain.entity.Invoice;

public interface InvoicePersistancePort {
    void saveInvoice(Invoice invoice);
}
