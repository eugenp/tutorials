package com.baeldung.method.info;

public class WithdrawLimitException  extends RuntimeException {
  public WithdrawLimitException(String message) {
    super(message);
  }
}
