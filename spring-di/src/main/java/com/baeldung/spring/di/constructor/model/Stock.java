package com.baeldung.spring.di.constructor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
  private String name;
  private String tradeType;
}
