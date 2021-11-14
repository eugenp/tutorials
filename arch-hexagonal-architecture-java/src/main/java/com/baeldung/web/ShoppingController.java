package com.baeldung.web;

import com.baeldung.functional.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController("/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    // API - read

    // API - write

    @PutMapping("/carts/{cart_no}/products/{product_no}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@PathParam("cart_no") Long cartNo, @PathParam("product_no") Long productNo, @RequestParam("quantity") Long quantity) {
        shoppingService.addProduct(cartNo, productNo, quantity);
    }

}
