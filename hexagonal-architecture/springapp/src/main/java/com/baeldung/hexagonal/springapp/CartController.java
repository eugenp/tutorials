package com.baeldung.hexagonal.springapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.Cart;
import com.baeldung.hexagonal.core.CouponNotFoundException;
import com.baeldung.hexagonal.core.ProductNotFoundException;
import com.baeldung.hexagonal.core.ShoppingCartService;

@RestController
@RequestMapping(path = "/cart")
public class CartController {

    private final ShoppingCartService shoppingCartService;

    public CartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public Cart getCart() {
        return shoppingCartService.obtainCart();
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addCartItem(@RequestBody CartItemAdditionRequest additionRequest) {
        try {
            this.shoppingCartService.addToCart(additionRequest.getProductId(), additionRequest.getQuantity());
            return ResponseEntity.ok().build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/coupons")
    public ResponseEntity<Void> applyCoupon(@RequestBody String couponCode) {
        try {
            shoppingCartService.applyCouponCode(couponCode);
            return ResponseEntity.ok().build();
        } catch (CouponNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
