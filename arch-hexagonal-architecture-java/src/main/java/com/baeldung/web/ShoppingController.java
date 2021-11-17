package com.baeldung.web;

import com.baeldung.functional.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    // API - read

    // API - write

    @PutMapping("/carts/{cart_no}/products/{product_no}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@PathVariable("cart_no") Long cartNo, @PathVariable("product_no") Long productNo, @RequestParam("quantity") Long quantity) {
        shoppingService.addProduct(cartNo, productNo, quantity);
    }

}
