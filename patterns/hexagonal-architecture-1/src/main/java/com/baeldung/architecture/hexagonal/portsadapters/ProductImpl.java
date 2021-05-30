package com.baeldung.architecture.hexagonal.portsadapters;

import com.baeldung.architecture.hexagonal.service.PriceFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductImpl implements Product {

  @Autowired
  PriceFetcher priceFetcher;

  @Override
  public int getPrice(String productId, int quantity) {
    if (productId == null || quantity > 10 || quantity < 1) {
      throw new RuntimeException("Quantity or product value is invalid");
    }
    return priceFetcher.getPrice(productId, quantity);
  }
}
