package com.baeldung.design.hex.adapter.driving;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.design.hex.business.domain.Item;
import com.baeldung.design.hex.business.service.IOrderService;
import com.baeldung.design.hex.port.in.IController;

@RestController
@RequestMapping("/api/order")
public class ControllerForExternalApplication implements IController {

    @Autowired
    IOrderService service;

    @Value("${props.external.validCallers}")
    List<String> validCallers;

    @Override
    public String placeOrder(List<Item> items, String caller) {
        String orderId = null;
        if (validCallers.contains(caller)) {
            orderId = service.processOrder(items, caller);
        }
        return orderId;
    }

    @Override
    public List<Item> getOrderedItems(String orderId, String caller) {
        List<Item> items = null;
        if (validCallers.contains(caller)) {
            items = service.getOrderedItems(orderId);
        }
        return items;
    }

}
