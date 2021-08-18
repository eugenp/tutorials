package com.baeldung.hexagonal.domain;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ShoppingCart {

  private final Map<CartItem, Integer> itemToQuantity;

  Float getTotalCartValue() {
    return itemToQuantity.entrySet().stream()
        .map(item -> item.getKey().getPrice() * item.getValue()).reduce(0f, Float::sum);
  }

  void addItem(CartItem cartItem, Integer quantity) {
    if (quantity < 0)
      throw new IllegalArgumentException("Quantity should not be negative.");
    itemToQuantity.put(cartItem, itemToQuantity.getOrDefault(cartItem, 0) + quantity);
  }
}
