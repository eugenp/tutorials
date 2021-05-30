package com.baeldung.architecture.hexagonal.controller;

import com.baeldung.architecture.hexagonal.ProductPrice;
import com.baeldung.architecture.hexagonal.portsadapters.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

  @Autowired
  Product product;

  @GetMapping(value = "/getprice", produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductPrice getPrice(@RequestParam("id") String productId,
                               @RequestParam("quantity") int quantity) {
    ProductPrice productPrice = new ProductPrice();
    productPrice.setPrice(product.getPrice(productId, quantity));
    return productPrice;
  }
}
