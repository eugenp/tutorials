package com.baeldung.design.hex.adapter.driving;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.design.hex.business.domain.Item;
import com.baeldung.design.hex.port.in.IOrderService;

@RestController
@RequestMapping("/api/order")
public class ControllerForExternalApplication {

    @Autowired
    IOrderService service;

    @Value("${props.external.validCallers}")
    List<String> validCallers;

    @PostMapping("/{caller}")
    public String placeOrder(List<Item> items, String caller) {
        String orderId = null;
        if (validCallers.contains(caller)) {
            orderId = service.processOrder(items, caller);
        }
        return orderId;
    }

    @GetMapping("/{orderId}/{caller}")
    public List<Item> getOrderedItems(String orderId, String caller) {
        List<Item> items = null;
        if (validCallers.contains(caller)) {
            items = service.getOrderedItems(orderId);
        }
        return items;
    }

}
