package com.baeldung.design.hex.adapter.driven;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.design.hex.adapter.driven.component.PrinterAPI;
import com.baeldung.design.hex.business.domain.Item;
import com.baeldung.design.hex.port.out.IPrinter;

@Service
public class InvoicePrinter implements IPrinter {

    @Autowired
    PrinterAPI printerAPI;

    @Override
    public void print(String orderId, List<Item> items) {
        printerAPI.printInvoice(orderId, items);
    }

}
