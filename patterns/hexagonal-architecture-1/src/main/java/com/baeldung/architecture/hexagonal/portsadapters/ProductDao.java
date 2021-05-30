package com.baeldung.architecture.hexagonal.portsadapters;

public interface ProductDao {
  public int getPrice(String productId);
}
