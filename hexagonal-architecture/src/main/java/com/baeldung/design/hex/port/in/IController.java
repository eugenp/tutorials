package com.baeldung.design.hex.port.in;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.design.hex.business.domain.Item;

public interface IController {
    // caller parameter to identify whether external or internal application is the caller
    @PostMapping("/{caller}")
    public String placeOrder(@RequestBody List<Item> item, @PathVariable String caller);

    @GetMapping("/{orderId}/{caller}")
    public List<Item> getOrderedItems(@PathVariable String orderId, @PathVariable String caller);
}
