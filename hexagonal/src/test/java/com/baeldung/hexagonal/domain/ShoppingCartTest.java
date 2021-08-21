package com.baeldung.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    @Test
    void givenNonemptyShoppingCart_whenGetTotalCartValueCalled_thenShouldCalculateCorrectly() {

        // given
        Map<CartItem, Integer> items = new HashMap<>();
        items.put(new CartItem("Cheese", 1.5f), 2);
        items.put(new CartItem("Lemon", 2f), 5);

        ShoppingCart cart = new ShoppingCart(items);

        // when
        Float totalCartValue = cart.getTotalCartValue();

        // then
        assertEquals(totalCartValue, 13f, 0);
    }
}
