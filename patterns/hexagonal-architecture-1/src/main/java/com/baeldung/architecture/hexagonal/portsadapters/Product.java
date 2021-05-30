package com.baeldung.architecture.hexagonal.portsadapters;

public interface Product {
  public int getPrice(String productId, int quantity);
}
