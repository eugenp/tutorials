package com.baeldung.hexagonal.persistance;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonal.domain.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoicePersistanceAdapter implements InvoicePersistancePort {

    private static List<Invoice> invoiceList;

    @Override
    public void saveInvoice(Invoice invoice) {
        //Static storage
        if(invoiceList == null){
            invoiceList = new ArrayList<>();
        }
        invoiceList.add(invoice);
    }
}
