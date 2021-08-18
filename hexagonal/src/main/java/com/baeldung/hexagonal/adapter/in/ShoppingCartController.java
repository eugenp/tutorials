package com.baeldung.hexagonal.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

  @Autowired
  ShoppingCartFacade apiAdapter;

  @GetMapping
  public Float getTotalCartValue() {
    return apiAdapter.getTotalCartValue();
  }

  @PostMapping()
  public void addItem(@RequestBody ItemTransportObject item) {
    apiAdapter.addItem(item);
  }
}
