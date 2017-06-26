package com.baeldung.teng.invoicing.domain;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

@Repository
public class InvoiceRepository {

    public Invoice getInvoice(String invoiceId) {
        return new Invoice(requireNonNull(invoiceId), new Date(), new Customer("BE2143756890", "Remi", "Georges"))
            .setItems(asList(new Item("Crayon", valueOf(12.99), BigDecimal.ONE, valueOf(7)),
                             new Item("Papier", valueOf(3.59), BigDecimal.TEN, valueOf(7)),
                             new Item("Bière", valueOf(1.49), valueOf(6), valueOf(19))));
    }
}
