package com.baeldung.design.hex.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baeldung.design.hex.business.domain.Item;
import com.baeldung.design.hex.port.out.IMessageSender;
import com.baeldung.design.hex.port.out.IOrderRepository;
import com.baeldung.design.hex.port.out.IPrinter;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IOrderRepository repository;

    @Autowired
    IPrinter printer;

    @Autowired
    @Qualifier("emailSender")
    IMessageSender emailSender;

    @Autowired
    @Qualifier("smsSender")
    IMessageSender smsSender;

    @Override
    public String processOrder(List<Item> items, String caller) {
        String orderId = repository.placeOrder(items);

        if (caller != null && caller.equals("KFC_OUTLET")) {
            printer.print(orderId, items);
        } else if (caller != null && caller.equals("KFC_APP")) {
            emailSender.send(orderId);
            smsSender.send(orderId);
        }

        return orderId;
    }

    @Override
    public List<Item> getOrderedItems(String orderId) {
        return repository.getOrderedItems(orderId);
    }

}
