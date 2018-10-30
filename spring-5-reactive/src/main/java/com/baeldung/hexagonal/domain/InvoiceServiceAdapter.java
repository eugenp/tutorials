package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;

import com.baeldung.hexagonal.domain.entity.Invoice;
import com.baeldung.hexagonal.hexagonal.persistance.InvoicePersistancePort;
import org.springframework.stereotype.Component;

@Component
public class InvoiceServiceAdapter implements InvoiceServicePort {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal FIXED_TAXES_PERCENTAGE = new BigDecimal(15);

    public static int invoiceCounter;

    public void storeNewInvoice(BigDecimal amount, InvoicePersistancePort persistancePort) {
        Invoice newInvoice = new Invoice();
        newInvoice.setCode(++invoiceCounter);
        newInvoice.setAmount(amount);
        newInvoice.setTaxes(amount.multiply(FIXED_TAXES_PERCENTAGE).divide(ONE_HUNDRED));
        newInvoice.setTotalAmount(amount.add(newInvoice.getTaxes()));
        persistancePort.saveInvoice(newInvoice);
    }
}
