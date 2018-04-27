
package com.baeldung.reactive.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

public class CryptoCurrency {

  private static final MathContext MATH_CONTEXT = new MathContext(2);

  private String name;

  private BigDecimal price;

  private Instant instant;

  public CryptoCurrency() {
  }

  public CryptoCurrency(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public CryptoCurrency(String ticker, Double price) {
    this(ticker, new BigDecimal(price, MATH_CONTEXT));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Instant getInstant() {
    return instant;
  }

  public void setInstant(Instant instant) {
    this.instant = instant;
  }

  @Override
  public String toString() {
    return "CryptoCurrency{" +
        "name='" + name + '\'' +
        ", price=" + price +
        ", instant=" + instant +
        '}';
  }
}
