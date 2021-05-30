package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.portsadapters.ProductDao;
import com.baeldung.architecture.hexagonal.portsadapters.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceFetcher {

  @Autowired
  ProductDao productDao;

  @Autowired
  Publisher publisher;

  public int getPrice(String productId, int quantity) {
    int price = quantity * productDao.getPrice(productId);
    publisher.publish(productId, price);
    return price;
  }
}
