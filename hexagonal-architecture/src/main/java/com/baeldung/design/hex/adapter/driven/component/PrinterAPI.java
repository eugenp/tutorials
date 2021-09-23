package com.baeldung.design.hex.adapter.driven.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baeldung.design.hex.business.domain.Item;

@Component
public class PrinterAPI {
    public void printInvoice(String orderId, List<Item> items) {
        System.out.println(orderId);
        System.out.println(items);
    }
}
