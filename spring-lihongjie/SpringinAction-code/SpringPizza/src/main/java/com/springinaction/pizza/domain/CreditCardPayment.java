package com.springinaction.pizza.domain;


public class CreditCardPayment extends Payment {
  private static final long serialVersionUID = 1L;

  public CreditCardPayment() {}
  
  private String authorization;
  public void setAuthorization(String authorization) {
    this.authorization = authorization;
  }
  
  public String toString() {
    return "CREDIT:  $" + getAmount() + " ; AUTH: " + authorization;
  }
}
