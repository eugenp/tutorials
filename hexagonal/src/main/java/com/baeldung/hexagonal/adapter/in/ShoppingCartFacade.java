package com.baeldung.hexagonal.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.ShoppingCartService;

@Component
class ShoppingCartFacade {

  @Autowired
  ShoppingCartService shoppingCartService;

  void addItem(ItemTransportObject item) {
    shoppingCartService.addItem(item.getName(), item.getQuantity());
  }

  Float getTotalCartValue() {
    return shoppingCartService.getTotalCartValue();
  }
}
