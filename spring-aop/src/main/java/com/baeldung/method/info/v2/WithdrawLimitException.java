package com.baeldung.method.info.v2;

public class WithdrawLimitException  extends RuntimeException {
  public WithdrawLimitException(String message) {
    super(message);
  }
}
