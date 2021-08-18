package com.baeldung.hexagonal.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class CartItem {

  @NonNull
  private final String name;
  private final Float price;

}
