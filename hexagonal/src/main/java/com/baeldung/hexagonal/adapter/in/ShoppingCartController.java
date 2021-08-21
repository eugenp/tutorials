package com.baeldung.hexagonal.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baeldung.hexagonal.domain.ShoppingCartService;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping
    public Float getTotalCartValue() {
        return shoppingCartService.getTotalCartValue();
    }

    @PostMapping()
    public void addItem(@RequestBody ItemTransportObject item) {
        shoppingCartService.addItem(item.getName(), item.getQuantity());
    }
}
