package com.baeldung.architecture.hexagonal.portsadapters;

public interface Publisher {
  public void publish(String productId, int price);
}
