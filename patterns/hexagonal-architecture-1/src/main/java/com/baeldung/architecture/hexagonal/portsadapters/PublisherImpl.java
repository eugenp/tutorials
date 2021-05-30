package com.baeldung.architecture.hexagonal.portsadapters;

import org.springframework.stereotype.Component;

@Component
public class PublisherImpl implements Publisher {

  @Override
  public void publish(String productId, int price) {
    System.out.println("Publishing productId " + productId + " and price " + price);
  }
}
