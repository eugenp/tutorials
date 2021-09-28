package com.baeldung.design.hex.adapter.driving;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.design.hex.business.domain.Item;
import com.baeldung.design.hex.port.in.IOrderService;

@RestController
@RequestMapping("/order")
public class ControllerForInternalAppication {

    @Autowired
    IOrderService service;

    @PostMapping("/{caller}")
    public String placeOrder(List<Item> items, String caller) {
        return service.processOrder(items, caller);
    }

    @GetMapping("/{orderId}/{caller}")
    public List<Item> getOrderedItems(String orderId, String caller) {
        return service.getOrderedItems(orderId);
    }

}
